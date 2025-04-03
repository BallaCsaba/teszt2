Working with Git
================

## Example Project

Building and running the project:

```shell
cd nationalize-client
mvn compile
mvn exec:java
mvn exec:java -Dexec.args=torvalds
mvn package
java -jar target/nationalize-client-1.0.jar
java -jar target/nationalize-client-1.0.jar torvalds
mvn clean
```

In the following, every command is supposed to be executed in the `nationalize-client` directory.

## Providing Your Identity

Provide your name and email address in the `developers/developer` element in the `pom.xml` file.

## Generating a  `.gitignore` File

See: <https://gitignore.io/>

```shell
curl -s -o .gitignore https://www.toptal.com/developers/gitignore/api/maven,intellij+all
```

## Initializing a Git Repository for the Project

```shell
git init
git status
git add .
git commit -m "initial import"
git status
git log
git log --oneline
```

## Making Changes in the Repository

Our task is to implement the `toString()` method in the `Nationality` class using the  [ReflectionToStringBuilder](https://commons.apache.org/proper/commons-lang/javadocs/api-release/org/apache/commons/lang3/builder/ReflectionToStringBuilder.html) class of the [Commons Lang](https://commons.apache.org/proper/commons-lang/) library.

In the `pom.xml` file, add the following `dependency` element to the `project/dependencies` element:

```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.17.0</version>
    <scope>compile</scope>
</dependency>
```

Resolve the dependency, then commit the changes in the repository with the following commands:

```shell
mvn dependency:resolve
git commit -am "build: add the commons-lang3 dependency to pom.xml" 
git log --oneline
```

Add the following import declarations to the `Nationality` class:

```java
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
```

Add the following method to both the `Nationality` class and its static nested class `Country`:

```java
@Override
public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
}
```

Let's check whether the program works correctly:

```shell
mvn compile exec:java -Dexec.args=torvalds
```

Let's examine the status of the working directory and then commit the changes in the repository:

```shell
git status
git diff
git commit -am "fix: implement toString() in Nationality with ReflectionToStringBuilder"
git log
```

## Working with Branches

Our next task is to configure the project to use [Lombok](https://projectlombok.org/), for which we create a new branch named `lombok`:

```shell
git branch
git branch lombok
git checkout lombok
git branch
```

In the `pom.xml` file, add the following `dependency` element to the `project/dependencies` element:

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.36</version>
    <scope>provided</scope>
</dependency>
```

In the `project/build/plugins` element, modify the `plugin` element of the `maven-compiler-plugin` to contain a `configuration` element as follows:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.13.0</version>
    <configuration>
        <annotationProcessorPaths>
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>1.18.36</version>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

Check whether the project is buildable, then commit the changes in the repository:

```shell
mvn compile
git status
git diff
git commit -am "build: add Lombok support to the project in pom.xml"
git log --oneline
```

In `Nationality.java`, delete the imports of `ReflectionToStringBuilder` and `ToStringStyle` and all constructors and methods. Thus, only field declarations should be kept in the source code. This also applies to the static nested class `Country`.

Add the `@lombok.Data` annotation right before the declaration of the `Nationality` and `Country` classes.

Let's check whether the program works correctly and then commit the changes in the repository:

```shell
mvn compile exec:java -Dexec.args=torvalds
git status
git diff
git commit -am "refactor: use Lombok in Nationality"
git log --oneline
```

Remove the now unused `commons-lang3` dependency (i.e., the containing `dependency` element) from the `pom.xml` file and commit the changes:

```shell
git status
git diff
git commit -am "build: delete the now unused commons-lang3 dependency from pom.xml"
git log --oneline
```

Note that we are now 3 commits ahead of the `master` branch.

Let’s switch back to the `master` branch and examine the version history:

```shell
git checkout master
git log --oneline
git log --oneline --all
```

Create a new file named `README.md` with the following content:

```markdown
nationalize-client
==================

TODO
```

Add the file to the repository:

```shell
git status
git add README.md
git commit -m "docs: add README.md"
```

Note that the two branches have diverged:

```shell
git log --oneline --all --graph
```

Let's switch back to the `lombok` branch and notice that there is no `README.md` file:

```shell
git checkout lombok
ls -l
```

Let's switch back to the `master` branch, merge the `lombok` branch into it, and then delete the `lombok` branch:

```shell
git checkout master
git merge lombok
git log --oneline --graph
git branch -d lombok
git log --oneline --graph
```

> [!NOTE] 
> The `git merge` command results in a commit; thus, it shows a text editor with an auto-generated message. Users can edit the message to explain and justify the merge or accept the auto-generated message. In both cases, you should exit from the text editor by saving the text.
>
> If the text editor is the dreadful [vi](https://ex-vi.sourceforge.net/), you can exit by pressing <kbd>Esc</kbd>, then typing `:wq`, and finally, pressing <kbd>Enter</kbd>.

## About the Change of the Name of the Default Branch on GitHub

Starting from October 1, 2020, the name of the default branch in every repository created on GitHub is `main` instead of `master`. Further information about this issue can be found [here](https://github.com/github/renaming).

Here, we present four ways to avoid problems caused by the change:

1. Set the name of the default branch to `main` with

   ```shell
   git config --global init.defaultBranch main
   ```

   for every new repository created with the command `git init` (requires version `2.28.0` or later).

1. Create your Git repository with the command

   ```shell
   git init -b main
   ```

   that sets the name of the default branch to `main` (requires version `2.28.0` or later).

1. Change the name of the default branch on GitHub to `master` [here](https://github.com/settings/repositories).

1. Rename the `master` branch to `main` with the command

   ```shell
   git branch -M main
   ```

   before pushing to GitHub for the first time.

## Pushing the Repository to GitHub

Create a repository named `nationalize-client` on GitHub by clicking [here](https://github.com/new) (requires login), and then execute the following commands in your working directory:

```shell
git remote add origin https://github.com/<username>/nationalize-client.git
git push -u origin master
```

## GitHub Authentication

When a Git client asks for your password to authenticate to GitHub, you can't use your account password. Instead, you must provide a personal access token that can be created [here](https://github.com/settings/tokens) (requires login). See [this](https://github.blog/2020-12-15-token-authentication-requirements-for-git-operations/) blog post for more details.

Alternatively, you can use [Git Credential Manager (GCM)](https://github.com/GitCredentialManager/git-credential-manager) for authentication via the browser using your GitHub account password.

## Undoing Changes

### Changing the Last Commit Message

Replace the last line of the `README.md` file (i.e., the text `TODO`) with the line below:

```markdown
Java class library to estimate the nationality of a person based on a last name.
```

Let's commit the changes (the typos are intentional):

```shell
git commit -am "docs: add contnent to REDME.md"
```

The last commit message can be changed with the following command:

```shell
git commit --amend -m "docs: add content to README.md"
git log --oneline
```

### Undoing Uncommitted Changes

Delete the `README.md` file and restore it as follows:

```shell
rm README.md
git status
git checkout -- README.md
ll
git status
```

The command `git reset --hard` also discards any uncommitted changes in the working directory.

### Undoing Committed Changes

Delete the `README.md` file and commit the changes in the repository. The commit can be reverted as follows:

```shell
git rm README.md
git commit -m "docs: delete README.md"
git log --oneline
git revert HEAD # reverts the last commit
git log --oneline
```

> [!NOTE] 
> By default, the `git revert` command also invokes a text editor like the `git merge` command. In this case, you should also do the same.

The last two commits can be deleted with the following command:

```shell
git reset --hard HEAD~2
```

## Working with Branches (2)

Let's modernize the project to use record classes introduced in Java SE 17. First, create a new branch named `jdk17` and switch to it:

```shell
git branch
git branch jdk17
git checkout jdk17
git branch
```

In the `pom.xml` file, change the `maven.compiler.release` property in the `project/properties` element to `17`, i.e.,

```xml
<maven.compiler.release>17</maven.compiler.release>
```

Examine and commit the change by executing the following commands:

```shell
git status
git diff
git commit -am 'build!: update Java SE version to 17 in pom.xml'
git log --oneline
```

Note that changing the JDK requirements is a breaking change, as indicated by the exclamation mark (`!`) that follows the commit type.

Change the `Nationality` class and its static nested class `Country` to record classes:

```java
package nationalize;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record Nationality(long count, String name, @SerializedName("country") List<Country> countries) {
    public record Country(String countryId, float probability) {}
}
```

Let's check whether the program works correctly:

```shell
mvn compile exec:java -Dexec.args=torvalds
```

If it works, it's time to examine and commit the changes:

```shell
git status
git diff
git commit -am 'perf!: change Nationality to a record class'
git log --oneline
```

Note that Lombok is not needed anymore. Thus, remove all Lombok-related elements (i.e., the corresponding `project/dependencies/dependency` element and also the `configuration` element with the `annotationProcessorPaths` element) from the `pom.xml` file and commit the changes:

```shell
git commit -am "build: remove Lombok support from the project in pom.xml"
```

Finally, let's push the `jdk17` branch to GitHub by executing the command

```shell
git push --set-upstream origin jdk17
```

Note that, in the following, changes made on the `jdk17` branch can be pushed to GitHub with the command

```shell
git push
```

(provided that `jdk17` is the current branch).

## Conflict Resolution

Let's switch to the `master` branch (i.e., `git checkout master`) and add JDK requirements to the end of the `README.md` file. Thus, its content should be

```markdown
nationalize-client
==================

Java class library to estimate the nationality of a person based on a last name.

Building the project requires JDK 11 or later.
```

Examine and commit the changes:

```shell
git status
git diff
git commit -am "docs: added JDK requirements (i.e., JDK 11) to README.md"
```

Let's also make a similar change on the `jdk17` branch. First, switch to the `jdk17` branch (i.e., `git checkout jdk17`). Note that the changes made in the `README.md` file on the `master` branch are not visible on the `jdk17` branch. Add JDK requirements to the end of the `README.md` file as follows:

```markdown
nationalize-client
==================

Java class library to estimate the nationality of a person based on a last name.

Building the project requires JDK 17 or later.
```

Examine and commit the changes:

```shell
git commit -am "docs: added JDK requirements (i.e., JDK 17) to README.md"
```

Examine the version history to see that the `master` and the `jdk17` branches have been diverged:

```shell
git log --oneline --all --graph
```

It's time to switch back to the `master` branch and merge the `jdk17` branch into it:

```shell
git checkout master
git merge jdk17
```

Note that, on the two branches the same part of the same file has been modified differently. Thus, the result of the merge operation is a conflict:

```
Auto-merging README.md
CONFLICT (content): Merge conflict in README.md
Automatic merge failed; fix conflicts and then commit the result.
```

The output of the

```shell
git status
```

command also indicates a conflict.

The conflict is shown in the `README.md` file as follows: 
```
<<<<<<< HEAD
Building the project requires JDK 11 or later.
=======
Building the project requires JDK 17 or later.
>>>>>>> jdk17
```

The conflict can be resolved by editing the `README.md` file and removing the unnecessary lines, including the conflict markers. Of course, in this case, you should keep only the line `Building the project requires JDK 17 or later`.

Finally, a commit is also needed:

```shell
git commit -a
```

Note that there is no need to explicitly provide a commit message; instead, a default message is shown in a text editor. The commit operation is performed when you exit from the text editor after saving the file.

Also, note that the version history contains a merge commit:

```shell
git log --oneline --all --graph
```
