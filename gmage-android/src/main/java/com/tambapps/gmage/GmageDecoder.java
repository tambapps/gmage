package com.tambapps.gmage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.tambapps.gmage.bitmap.BitmapUtils;
import com.tambapps.gmage.exception.GmageDecodingException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Base64;

/**
 * Class providing functions to decode a Gmage from many kinds of objects
 */
public final class GmageDecoder {

  private GmageDecoder() {
    throw new RuntimeException("You can't touch this");
  }

  /**
   * Tries to decode a Bitmap from an Object
   *
   * @param object the object to decode
   * @return the decoded Gmage
   * @throws UnsupportedOperationException in case the object could not be converted
   * @throws GmageDecodingException        in case an error occurred during the decoding
   */
  public static Gmage decodeFrom(Object object)
      throws UnsupportedOperationException, GmageDecodingException {
    try {
      Bitmap bitmap = BitmapUtils.decodeFrom(object);
      return decode(bitmap);
    } catch (IOException e) {
      throw new GmageDecodingException(e.getMessage(), e);
    }
  }

  /**
   * Decode Gmage from a Bitmap
   *
   * @param bitmap the bitmap to decode
   * @return the decoded Gmage
   * @throws GmageDecodingException in case an error occurred during the decoding
   */
  public static Gmage decode(Bitmap bitmap) throws GmageDecodingException {
    // result of BitmapFactory.decodeSomething() is null if an error occurred
    if (bitmap == null) {
      throw new GmageDecodingException("Couldn't decode image");
    }
    return BitmapUtils.toGmage(bitmap);
  }

}
