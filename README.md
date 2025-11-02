# BitmapFontBridge
Use FontWriter's Structured JSON fonts as libGDX BitmapFonts.

## tl;dr
Add this Gradle dependency to core/build.gradle in your libGDX project :

```groovy
api "com.github.tommyettinger:bitmapfontbridge:0.0.2"
```

If you have a GWT subproject (also called `html`), add this dependency to html/build.gradle :

```groovy
api "com.github.tommyettinger:bitmapfontbridge:0.0.2:sources"
```

And also for GWT, add this line after other "inherits" lines in your `GdxDefinition.gwt.xml` file, which is somewhere
inside your html project's src/main/java folder:

```xml
<inherits name="com.github.tommyettinger.bridge />
```

BitmapFontBridge requires libGDX 1.13.1 or higher; it doesn't make use of any APIs that had breaking changes in
libGDX 1.14.0 (or 1.13.5).

## Why?
[FontWriter](https://github.com/tommyettinger/fontwriter) produces high-quality bitmap fonts that tend not to need any
fiddling with generated files, and pretty much "just work." However, libGDX defaults to loading its BitmapFont objects
from AngelCode BMFont .fnt files, and FontWriter produces "Structured JSON" files, which are nothing like .fnt files.
This library allows a BitmapFont to be loaded from a Structured JSON file and an image file or TextureRegion.

The JSON files can have a .json file extension, but are more often compressed with libGDX's own `Lzma` class and have
the .json.lzma file extension. Some older files use the .dat extension, which here indicates they use a custom
compression algorithm (one that isn't as good at having small file sizes), and some others use .ubj or .ubj.lzma
extensions, for the binary UBJSON format or a UBJSON file compressed with LZMA. If you use libGDX 1.13.1, your best
option is .json for a human-readable file, or .json.lzma for a small file. Later versions of libGDX can use .ubj.lzma
files, which are smaller than .json.lzma by a little bit, but libGDX 1.13.1 has a bug on GWT when trying to read UBJSON
files; it was fixed in later releases. You may still want to prefer .json.lzma because there isn't an easy way to read
or validate a .ubj file in the way that a .json file can be read by a human being. This library provides the `LzmaUtils`
class that can extract any file compressed by libGDX's `Lzma` tool to a different file, and similarly compress one back.

You can directly load a BitmapFont using `BitmapFontSupport` here, or replace your usages of scene2d.ui `Skin` with this
library's `JsonSkin`, which can load the same files but is also able to make sense of Structured JSON fonts, as well as
the existing .fnt format. You can use `JsonSkinLoader` with an `AssetManager`, also.

## Changes
Version 0.0.2 makes sure BitmapFonts are scaled according to their cap height, rather than their line height as before.
This matches the behavior of BitmapFonts loaded from a Skin with built-in libGDX code. This is the only change, but it
does affect how BitmapFonts will look in your program.

## TextraTypist
The classes in here were brought out from [TextraTypist](https://github.com/tommyettinger/textratypist) and made into
their own library so code that wants to only use BitmapFont can. If you want to use Structured JSON fonts with
TextraTypist, it already can load them and also includes a bunch of them in its `knownFonts/` folder. It defaults to
creating one of its own `Font` objects in addition to a `BitmapFont` whenever it loads a Structured JSON file, and most
of TextraTypist is built around using Font in an API that mirrors scene2d.ui's widgets, but uses Font instead of
BitmapFont. Using Font allows TextraTypist to do various things BitmapFont can't or has difficulty doing, like drawing
text as bold, oblique (which is slanted, like italic), underlined, struck-through, with inline images mixed in (often,
these are full-color emoji), and so on. TextraTypist also can use various effects on text, ranging from typing out each
letter at configurable speed, to moving text as if it is in a windstorm, changing its color in a rainbow cycle or with
randomized fiery colors, slamming letters into position, fading letters in gently, and even interacting with user input,
such as having clickable links or making clicks on words trigger events. Some things BitmapFont does better, like
working with fallback fonts via FreeType, though this library doesn't have anything that uses FreeType.

This concludes the advertisement for TextraTypist. Which is free, like this library. How free? Well...

## License
[Apache License 2.0](LICENSE). You can use this library without needing to credit anyone, and can use it in commercial
software without needing to reveal source code. If you make modifications to this code, you do not have to share them,
and a common way of using this small library is to copy only the necessary files into your own project.