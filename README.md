# Elucidator

![Maven Central](https://img.shields.io/maven-central/v/me.omico.elucidator/elucidator)
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/me.omico.elucidator/elucidator?server=https%3A%2F%2Fs01.oss.sonatype.org)

Elucidator aims to provide a better Kotlin DSL experience for [kotlinpoet](https://github.com/square/kotlinpoet).

Currently, it is still in the work-in-progress phase and not ready for production use. This means the API is unstable, may change in the future, and may be missing some features.

## Usage

```kotlin
repositories {
    // For stable releases only
    mavenCentral()
    // Or for snapshots only
    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation("me.omico.elucidator:elucidator:<version>")
}
```

## License

```txt
Copyright 2023 Omico

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
