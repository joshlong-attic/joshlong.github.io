#!/bin/bash

echo -e "deploying updates to Github.."

# Build the project
rm -rf output
jbake

git add output

cp -r content/media output/media
git add output/media
git commit -a -m "adding media"

cp CNAME output/CNAME
git add output/CNAME
git commit -a -m "adding CNAME"

git add -a

# Commit changes.
msg="rebuilding site `date`"
if [ $# -eq 1 ]
  then msg="$1"
fi
git commit -m "$msg"

# Push source and build repos.
git push origin source
git subtree push --prefix=output git@github.com:joshlong/joshlong.github.io.git master

echo "the page is available now as http://github.com/joshlong/joshlong.github.io.git"
