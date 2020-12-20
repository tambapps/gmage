# Gmage

**Gmage** is a Groovy-friendly image processing library, written in Java.

## Examples
### Java

```groovy
// TODO
```
### Groovy
```groovy
// TODO
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
- JPG
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

## Colors
// TODO talk about int and number colors, rgb/argb
talk about operators?

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
You can blur an image with different algorithms.

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