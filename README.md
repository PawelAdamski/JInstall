# JInstall
## Overview
JInstall is an Maven plugin which allows you to install Java application on your local machine.

## Example
Imagine you have created ping application in Java. Let's call it JPing.
Java application are distributed as Jar files. So to launch it, you need to write

```bash
java -jar jping.jar example.com
```

but with JInstall plugin you can write `mvn install`. It will create a launching file that allows you write

```bash
jping example.com
```

## Set up
Before start using `JInstall` you must set up enviroment variable `JAR_PATH`.
