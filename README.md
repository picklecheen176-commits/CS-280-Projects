## CS-280-Projects

[![Stable](https://img.shields.io/badge/docs-stable-blue.svg)](https://picklecheen176-commits.github.io/CS-280-Projects/)
[![GitHub Actions CI](https://github.com/picklecheen176-commits/CS-280-Projects/actions/workflows/CI.yml/badge.svg)](https://github.com/picklecheen176-commits/CS-280-Projects/actions/workflows/CI.yml?query=branch%3Amain)

This is the starter package for the Holy Cross College CS 280 Data Structures course.

## Folder Structure

Any Java workspace contains the following folders:
- `src`: contains your source code in `.java` files. Normally, the files in this folder are the only ones you ever need to modify manually.
- `lib`: contains external dependencies, in the form of .jar files. For this class, you will be making use of the code at [this repository](https://github.com/ksherbert-hcc/AbstractDataTypes).

This repository also contains the following eccentricities:
- `.github`: contains the "Continuous Integration" configuration which ensures GitHub builds javadocs and runs tests whenever you push your code upstream. Understanding the intricacies of continuous integrations is beyond the scope of this course, but it's a good exercise to try and understand what is happening here if you have the time.
- `.vscode`: contains the IDE configuration which ensures VS Code knows how the rest of these folders fit in. Even though I have you run code through the command line rather than the IDE interface, this is still necessary for real-time linting, i.e. for VS Code to identify errors in your code as you write it
- `tutorials`: contains step-by-step guides for interacting with git and Java. They also double as the first lab of the course.

Finally, the following folders will be automatically created as you develop your code:
- `bin`: contains compiled `.class` files. Note that these files can be platform dependent, so they should always be compiled on the fly, and never committed to git history.
- `docs`: contains deployed `.html` files when building javadocs. Normally, you will view javadocs through the website GitHub builds for you in continuous integration, but you may build the docs locally and then open the `docs/index.html` file in your browser if you would like to view documentation for code that isn't ready to be pushed to GitHub yet.


## Command Line Recipes
#### Run a File
```shell
CS-280-Projects> java -cp lib/* src/Path/To/File.java
```

#### Generate Documentation
```shell
CS-280-Projects> javadoc -d docs -sourcepath src -subpackages assignments
```

#### Run Unit Tests
```shell
CS-280-Projects> java -cp lib/* -ea src/Test.java
```

## First Time Here?
Good morning! Today's lab is broken up into three parts:
1. [One-time Setup](tutorials/setup.md)
2. [Learning Git](tutorials/git.md)
3. [Learning Java](tutorials/java.md)

I've broken them up into separate files so you can bookmark them easily for future reference.
Remember to ask for help if you get stuck!