package demo;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ReaderInputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}


@Component
class Migrator implements CommandLineRunner {

    private final File root;
    private final File images;
    private final Log log = LogFactory.getLog(getClass());
    private JdbcTemplate template;
    private final Map<Long, ManagedUpload> managedUploads = new ConcurrentHashMap<>();

    @Autowired
    public Migrator(@Value("${blog.content-root}") File root, JdbcTemplate template) {
        this.root = root;
        this.template = template;
        Assert.notNull(this.root, "the content root should be non-null!");
        this.images = new File(this.root, "media");
        Assert.isTrue(this.images.exists() || this.images.mkdirs(),
                String.format("the media directory, %s, does not exist and could not be created", this.images.getAbsolutePath()));
    }

    protected void download(ManagedUpload mu) {
        try {
            File dl = new File(this.images, mu.getId() + "");

            if (!dl.exists()) {
                try (InputStream in = new URL("http://joshlong.com/api/managedUploads/" + mu.getId()).openStream();
                     OutputStream out = new FileOutputStream(dl)) {
                    IOUtils.copy(in, out);
                }
                this.managedUploads.putIfAbsent(mu.getId(), mu);
            }
        } catch (Exception ex) {
            log.debug("exception trying to download image " + ex.toString());
        }
    }

    protected void emitBlog(Blog blog) {
        Assert.notNull(blog, "the blog must be non-null!");
        File blogFile = new File(root, blog.getSeoUrl() + ".html");
        try {
            String newLine = System.getProperties().getProperty("line.separator");
            String frontMatter = String.format("title=%s" + newLine +
                            "date=%s" + newLine +
                            "type=post" + newLine +
                            "tags=blog" + newLine +
                            "status=published" + newLine +
                            "~~~~~~",
                    blog.getTitle(),
                    blog.getCreated().toString()
            );

            String body = String.format("%s%s%s%s", frontMatter, newLine, newLine, blog.getBody().replaceAll("/api/managedUploads/", "/media/"));

            if (!blogFile.exists()) {
                try (InputStream inputStream = new ReaderInputStream(new StringReader(body));
                     OutputStream outputStream = new FileOutputStream(blogFile)) {
                    IOUtils.copy(inputStream, outputStream);
                }
            }
            blog.getManagedUploads().forEach(this::download);

            this.log.debug(blog.toString());

        } catch (Exception ex) {
            this.log.error(String.format("exception during emitBlog(%s)",
                    blog.toString()), ex);
        }
    }

    @Override
    public void run(String... strings) throws Exception {
        RowMapper<Blog> blogRowMapper = (rs, i) ->
                new Blog(rs.getString("TITLE"), rs.getDate("PUBLISHED"), true, rs.getString("SEO_URL"), rs.getString("BODY"));
        String sqlQuery = "select * from blog where published is not null";
        this.template.query(sqlQuery, blogRowMapper).forEach(this::emitBlog);
    }
}

class ManagedUpload {
    private Long id;
    private String url;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ManagedUpload{");
        sb.append("id=").append(id);
        sb.append(", url='").append(url).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        ManagedUpload that = ManagedUpload.class.cast(o);
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getUrl() {
        return url;
    }

    public ManagedUpload(Long id, String url) {

        this.id = id;
        this.url = url;
    }
}

class Blog {
    private String title;
    private Date created;
    private boolean published;
    private Set<ManagedUpload> managedUploads = new HashSet<>();

    private String body;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Blog{");
        sb.append("title='").append(title).append('\'');
        sb.append(", created=").append(created);
        sb.append(", published=").append(published);
        sb.append(", seoUrl='").append(seoUrl).append('\'');
        sb.append(", managedUploads='").append(managedUploads).append('\'');
        sb.append(", body='").append("..").append('\'');
        sb.append('}');
        return sb.toString();
    }

    private String seoUrl;

    public Set<ManagedUpload> getManagedUploads() {
        return managedUploads;
    }

    public String getTitle() {
        return title;
    }

    private Collection<ManagedUpload> extractManagedUploads(String html) {
        List<ManagedUpload> managedUploadList = new ArrayList<>();
        Pattern p = Pattern.compile("/api/managedUploads/(\\d+)");
        Matcher matcher = p.matcher(html);
        while (matcher.find())
            managedUploadList.add(new ManagedUpload(Long.parseLong(matcher.group(1)),
                    matcher.group()));
        return managedUploadList;
    }


    public Date getCreated() {
        return created;
    }

    public boolean isPublished() {
        return published;
    }

    public String getSeoUrl() {
        return seoUrl;
    }

    public String getBody() {
        return body;
    }

    public Blog(String title, Date created, boolean published, String seoUrl, String body) {

        this.title = title;
        this.created = created;
        this.published = published;
        this.seoUrl = seoUrl;
        this.body = body;
        this.managedUploads = new HashSet<>(this.extractManagedUploads(this.body));

    }
}
