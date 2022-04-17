package com.tambapps.gmage

import com.tambapps.gmage.color.Color
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class GmageTest {

  @Disabled
  @Test
  void testShow() {
    Gmage gmage = new Gmage(400, 600, Color.BLUE)
    gmage.show()
    Thread.sleep(5000)
  }

}
