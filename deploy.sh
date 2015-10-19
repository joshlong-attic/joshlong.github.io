#!/bin/bash

echo -e "deploying updates to Github.."

# Build the project.
rm -rf output
jbake
#cp CNAME output/CNAME
#git add output/CNAME
#git commit -a -m "adding CNAME"
git add output 
git add -A

# Commit changes.
msg="rebuilding site `date`"
if [ $# -eq 1 ]
  then msg="$1"
fi
git commit -m "$msg"

# Push source and build repos.
git push origin source
git subtree push  --prefix=output git@github.com:joshlong/joshlong.github.io.git master
