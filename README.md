# Gmage

**Gmage** is a Groovy-friendly image processing library, written in Java.

## Examples
### Java

```groovy
Gmage gmage = GmageDecoder.decode('image.jpg');
gmage.apply(ColorTransformers.duoTone(Color.BLUE));
gmage.apply((color) -> color.and(0xff00ff0f));
gmage.apply(ColorTransformers.replaceColor(Color.BLUE, Color.CLEAR, 0.25f));
def blurred = gmage.blurred(new BoxBlur(BoxBlur.SHARPENING_KERNEL));
def resized = blurred.scaledBy(0.8f, 0.8f);
GmageEncoder.encode(resized, CompressFormat.PNG, new File("output.jpg"));
```

### Groovy
```groovy
Gmage gmage = GmageDecoder.decode('image.jpg')
gmage.apply(ColorTransformers.duoTone(Color.BLUE))
gmage.apply { Color color -> color | 0xff00ff0f }
gmage.apply(ColorTransformers.replaceColor(Color.BLUE, Color.CLEAR, 0.25f))
def blurred = gmage.blurred(new BoxBlur(BoxBlur.SHARPENING_KERNEL))
def resized = blurred.scaledBy(0.8f, 0.8f)
GmageEncoder.encode(resized, CompressFormat.PNG, new File("output.jpg"))
```

## Project architecture
The project has different modules

### core
This module is where the magic happens. It holds all the classes used for image processing

### desktop
It provides a `GmageEncoder` and `GmageEncoder` allowing to read/write images, converting them from/to Gmage.

The decoding/encoding is backed by Java `BufferedImage`
### android
It provides the same capabilities of the desktop module, but for android.

Since android don't have the JDK `BufferedImage`, encoding/decoding is backed by Android SDK `Bitmap`

## Colors
Before presenting features, let's talk about the representation of a `Color` in **Gmage**.
A `Color` has 4 channels: alpha, red, green, blue. Each channel have its value between 0 and 255.

You can create a Color from numbers (e.g `0xffffff`) but be careful,
**the type of the provided number (Integer, Long, etc...) is significant for the color conversion**.

int/Integer values are interpreted as RGB. The default alpha is always 255
```groovy
Color white = new Color(0xffffff)
```

Other values are interpreted as ARGB values
```groovy
Color white = new Color(0xffffffffL)
// note that in Groovy, divisions produce BigDecimal numbers
Color white2 = new Color(0xffffffff / 1)
```

This choice was made because in Groovy, `0xffffff` (RGB white) is interpreted as an Integer whereas
`0xffffffff` (ARGB white) is interpreted as a Long.

## Features

### Read/Write an image
Must-have for an image processing library.
To read/write an image, you will have to use either the desktop or android module.
The modules have the same classes, and same methods. The only differences are their implementation
and the formats supported. 

As seen in the `CompressFormat` enums, desktop supports:
- JPG
- PNG
- GIF
- BMP


And android supports:
- JPG
- PNG
- WEBP


here are some examples

### Java
```groovy
Gmage gmage = GmageDecoder.decode(new File("image.jpg"));
doSomething(gmage);
GmageEncoder.encode(gmage, CompressFormat.PNG, new File("output.png"));
```
### Groovy
```groovy
Gmage gmage = GmageDecoder.decode(new File("image.jpg"))
doSomething(gmage)
GmageEncoder.encode(gmage, CompressFormat.PNG, new File("output.png"))
```

## Modify pixels
You can access pixels by their (x,y) indexes, or a 1-D index. The name `getAt` and `putAt` were
chosen to allow Groovy operator overloading

### Java
```groovy
Gmage gmage = GmageDecoder.decode(new File("image.jpg"));
gmage.putPixel(1, 2, Color.RED);
gmage.apply((color) -> color.and(0xff00ff00));
Color c1 = gmage.getAt(1, 2);
Color c2 = gmage.getAt(3);
gmage.forEachPixel((c) -> System.out.println(c));
// operate on a region
gmage.apply(Color::negative, new BoxRegion(0, 0, gmage.getWidth() / 2,  gmage.getHeight() / 2));
```

### Groovy
```groovy
Gmage gmage = GmageDecoder.decode(new File("image.jpg"))
gmage[1, 2] = Color.RED
gmage.apply { Color color ->
  color & 0xff00ff00
}
Color c = gmage[1, 2]
Color c2 = gmage[3]
gmage.forEachPixel {
  System.out.println(it)
}
// operate on a region
gmage.apply({ Color color -> - color}, new BoxRegion(0, 0, gmage.width / 2,  gmage.height / 2));
```

### Predefined operations
You can find some predefined `ColorTransformer` in the `ColorTransformers` class.

## Scaling
You can scale an image with different algorithms.

### Java
```groovy
Gmage scaled = gmage.scaledBy(Scaling.BILINEAR_INTERPOLATION, 1.5, 2);
```

### Groovy
```groovy
def scaled = gmage.scaledBy(Scaling.NEAREST_NEIGHBOR, 1.5, 2)
```

## Blur
You can blur an image with different algorithms (for now only box blur is supported).

### Java
```groovy
Blur blur = new BoxBlur(BoxBlur.SMOOTHING_KERNEL);
Gmage blurred = gmage.blurred(blur);
```

### Groovy
```groovy
def blur = new BoxBlur(BoxBlur.SMOOTHING_KERNEL)
def blurred = gmage.blurred(blur)
```