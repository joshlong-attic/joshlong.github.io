#!/bin/bash

echo -e "deploying updates to Github.."

# Build the project
rm -rf output
jbake  . output --reset

git add output

cp -r content/media output/media
git add output/media
git commit -a -m "adding media"

cp CNAME output/CNAME
git add output/CNAME
git commit -a -m "adding CNAME"

git add -A

# Commit changes.
msg="rebuilding site `date`"
if [ $# -eq 1 ]
  then msg="$1"
fi
git commit -m "$msg"

git push origin source

git push origin `git subtree split --prefix output source`:master --force
# if you get some sort of error about not being able to push to unqualified master, comment out the line before and uncomment the line after
# git push origin `git subtree split --prefix output source`:refs/heads/master --force

echo "the page is available now as http://github.com/joshlong/joshlong.github.io.git"
