# PlantUML Vitruv


**PlantUML Vitruv** is a Java library designed to provide a structured, object-oriented domain model for generating PlantUML diagrams. It eliminates the need for manual string concatenation by providing a type-safe API for building complex architecture diagrams, with specific support for **ArchiMate** and **Edgy** modeling styles. It is also able to generate consolidated views if there are alot of leafs and dependencies between leafs in groups. Try it out. ;-)

## 📦 Project Structure

The library is organized into a core logic layer and a comprehensive styling engine:

* **Core Model**: Contains definitions for `Block`, `Leaf`, `Group`, and `Canvas` to build hierarchical diagrams.
* **Connections**: Manages relationships using `Connection`, `GroupConnection`, and various `ConnectionType` enums.
* **Styling**: A dedicated `style` package providing control over `Color`, `FontStyle`, `LineStyle`, and `Sprites`.
* **Specialized Types**: Built-in support for `ArchimateLeafType` and `EdgyLeafType` to ensure industry-standard notation.

## 🚀 Key Components

### 1. Layout & Containers
* **Canvas**: The root container that holds all diagram elements.
* **Group**: A structural element used to nest blocks and other groups, ideal for showing boundaries or system landscapes.
* **Leaf**: The primary node representing individual components, actors, or objects.
* **Paragraph**: Allows for multi-line text blocks within diagram elements.

### 2. Relationships
* **Connection**: Defines the link between two specific elements.
* **ArchimateConnectionType**: Supports standard ArchiMate relationships like *Composition*, *Assignment*, and *Realization*.
* **EdgyConnectionType**: Offers stylized visual connection types for modern, "edgy" diagram aesthetics.

### 3. Visual Customization
* **CustomStyle**: Allows for fine-grained overrides of element backgrounds, borders, and margins.
* **RenderConfig**: Settings that dictate how the final PlantUML code is formatted (e.g., skinparams).
* **StringUtil**: Utility for sanitizing and formatting labels for PlantUML compatibility.

## 🛠️ Build Information

This project is built with **Maven**. 

* **Group ID**: `ch.braincell`
* **Artifact ID**: `plantuml.vitruv`
* **Version**: `1.0.0`

# Installation

[![Maven Central](https://img.shields.io/maven-central/v/ch.braincell/plantuml.vitruv.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/ch.braincell/plantuml.vitruv)

This library is published on [Maven Central](https://central.sonatype.com/artifact/ch.braincell/plantuml.vitruv). You can include it in your project using any of the build tools listed below.

## Build Tool Dependency

### Maven

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>ch.braincell</groupId>
    <artifactId>plantuml.vitruv</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle (Groovy)

Add this line to your `build.gradle`:

```groovy
implementation 'ch.braincell:plantuml.vitruv:1.0.0'
```

### Gradle (Kotlin)

Add this line to your `build.gradle.kts`:

```kotlin
implementation("ch.braincell:plantuml.vitruv:1.0.0")
```
