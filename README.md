This is an example project for [MWAR-433](https://issues.apache.org/jira/browse/MWAR-433).

There seems to be a bug introduced in version `3.3.0` of the 
[maven-war-plugin](https://maven.apache.org/plugins/maven-war-plugin/). My project
generates wsdls using the `jaxws-maven-plugin` which puts the generated wsdls in 
`${project.build.directory}/${project.build.finalName}/WEB-INF/wsdls` so that they
are packaged up in the war file. Then I have a client jar that copies those wsdls 
out of the war file using the `maven-dependency-plugin` and generates a client from 
the wsdls using the `jaxws-maven-plugin`.

This all works fine using `3.2.3` of the `maven-war-plugin`.  However, after 
upgrading to `3.3.0` my project fails to build because the wsdls are no longer
preserved.  It seems that they are being deleted by the `maven-war-plugin`.

This example project recreates the problem.  If you run the following from the
`master` branch which is using version `3.2.3` of the `maven-war-plugin`
everything should work correctly:

```sh
$ ./mvnw clean install
``` 

You can also validate that the wsdl is present in the expanded war:

```
$ ls -la example-webapp/target/example-webapp-1.0-SNAPSHOT/WEB-INF/wsdl/ExampleWebService.wsdl
```

I've also included the `enunciate-maven-plugin` which should generate documentation
for the webservice in `${project.build.directory}/${project.build.finalName}/docs` just
to demonstrate that this isn't an issue specific to wsdl files.  You can validate that
the documentation is present like this:

```sh
$ ls -la example-webapp/target/example-webapp-1.0-SNAPSHOT/docs/index.html
```

Now if you checkout the `maven-war-plugin-3.3.0` branch you will see that the build will
fail when building the `example-client` module because the wsdl files is not present. Also,
if you do the same checks for the wsdl and the enunciate documentation those will also show
the files are no longer present.

You can also see build results here:

* master - [![Build Status](https://travis-ci.org/klieber/maven-war-plugin-bug.svg?branch=master)](https://travis-ci.org/klieber/maven-war-plugin-bug)
* maven-war-plugin-3.3.0 - [![Build Status](https://travis-ci.org/klieber/maven-war-plugin-bug.svg?branch=maven-war-plugin-3.3.0)](https://travis-ci.org/klieber/maven-war-plugin-bug)

