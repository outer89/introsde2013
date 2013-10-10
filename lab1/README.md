Git, Java and ANT
===
Introduction to Service Design and Engineering 2013/2014. 
<br>*Lab session #3*
<br>**University of Trento** 

---

## Outline

* Brief intro to the course lab sessions workflow
* Brief intro to Git
* Introduction to Ant and Java (developed in lab session 2)

---

## Git Basics
Download [git](http://git-scm.com/download)

Config your identity: 

```
	git config --global user.name ”Pinco Pallino"
	git config --global user.email pinco@pallino.it
	git config –-list
```

You can use git help <verb> to get help 

```	
	git help config
```	

Create your first test repository 

```
	mkdir introsde2013-test
	cd introsde2013-test
	git init
```

---

**What’s new in introsde2013-test?**
Take a look to the .git folder. That's where the git filesystem is stored. Every single thing you create/modify, is stored there as an object with hash identifier. The .git/config has details about the repository such as the origin remote (the server where the code will be pushed if there is one). 

---
**Git File Lifecycle**

![](https://raw.github.com/cdparra/introsde2013/master/lab1/resources/GitFilesLifcycle.png)

---

Let’s create a new file```	touch README	git status
```
**Observation:** if you're using windows, just create the README file in filesystem GUI (touch is shell program that creates a new file in unix/linux like systems)Stage your file
```
	git add README	git status```Commit your file
```	git commit –m “new README”!	git status```

---
That's it! You created a local repository, added a file and committed to the repository. This the basic you need to know. 

---

## Git in this course
In this course, all the material for the lab sessions will be pushed in [this repository](https://github.com/cdparra/introsde2013). To make sure you will always get the new content for each lab session, you need to: 

* Fork the repo in github
* Clone your fork in your local mahines
* Add the original repository (this one) as a remote from where to fetch future updates

To do this, follow these steps

---

### Fork this repo

```
	Go to https://github.com/cdparra/introsde2013.git
	Click on "Fork" in the upper right corner of the page
```
	
Now, you have a copy of the lab repository in your github account, in a URL like the following: 

```
	https://github.com/YOUR_GITHUB_USERNAME/introsde2013.git
```

---

### Clone your fork

On your shell, go out of the test repo we created earlier and execute this outside (or wherever you want) 
```
	git clone https://github.com/YOUR_GITHUB_USERNAME/introsde2013.git
```
	
Now, you have a copy of the lab repository in your laptop/computer in the folder 

```
	PATH_OF_THE_DIRECTORY_WHERE_YOU_CALLED_GITCLONE/introsde2013 
```

---

### Add the original repo as a remote

Adding the original repository as a remote it is important so that you can "fetch" future lab sessions materials. 

From the shell command line, enter your repository folder and follow these steps: 

```
	git remote add upstream https://github.com/cdparra/introsde2013.git
```

Now, the following two commands **must be executed before each lab session** 

```
	git fetch upstream
	git merge upstream/master
```

Git merge download new files/changes in the original repo (without modifying your own) git merge apply these changes.

---

### Conflicts
	
After the merge, there are two posibilities: 

* there are no conflicts
* there are conflicts. 

If no conflict exist, the file ".git/MERGE_MSG" will be automaticall open/displayed, with a merge commit message in it. Just saved and exit the file and the merge will be completed with output similar to this: 

```
	Merge made by the 'recursive' strategy.
 		lab1/solutions/Ex1.md | 4 +++-
 	1 file changed, 3 insertions(+), 1 deletion(-)
```

---

If there are conflicts, you will probably see the following output

```
	Auto-merging README.md
	CONFLICT (content): Merge conflict in README.md
	Automatic merge failed; fix conflicts and then commit the result. 
```

---

### Fix conflicts 

To fix the conflicts, you can either do it manually (opening the file and solving the conflicts as suggested [here](https://help.github.com/articles/resolving-merge-conflicts)) or use the "git mergetool". For this time, let's do it manually (you can later learn how to setup and use a visual mergetool)

So, fixing manually: open the file with the conflicts (README.md) and you will see something like this

	<<<<<<< HEAD
	your changes will be here
	=======
	the changes made in the "upstream" repository will be here
	>>>>>>> upstream/master

---

Choose either the paragraph above ======= or the one below and remove the other. For example, let's select your local changes and this section should be like the following.

	your changes will be here

---
	
If there is any other block of text like the one we saw before, you need to do the same. Once yo are done, there are some files that you just need to delete from the folder (remaining of the conflict resolution)

	README.md.BACKUP.72417.md 
	README.md.BASE.72417.md   
	README.md.LOCAL.72417.md  
	README.md.REMOTE.72417.md  

Now you can stage, commit and push the fixed version of README.md

	git add README.md
	git commit -m "conflict solved"
	git push origin master 

## Java and ANT

We didn't get here in the 2013 version of the lab. You will find this in [Lab2](https://github.com/cdparra/introsde2013/tree/master/lab2)
