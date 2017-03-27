package com.github.theobjop.engine.loader.obj.builder;

// This code was written by myself, Sean R. Owens, sean at guild dot net,
// and is released to the public domain. Share and enjoy. Since some
// people argue that it is impossible to release software to the public
// domain, you are also free to use this code under any version of the
// GPL, LPGL, Apache, or BSD licenses, or contact me for use of another
// license.  (I generally don't care so I'll almost certainly say yes.)
// In addition this code may also be used under the "unlicense" described
// at http://unlicense.org/ .  See the file UNLICENSE in the repo.

import org.joml.Vector3f;

import java.util.*;
import java.text.*;
import java.io.*;
import java.io.IOException;

public class ReflectivityTransmiss {

    public boolean isRGB = false;
    public boolean isXYZ = false;
    public double rx;
    public double gy;
    public double bz;

    public ReflectivityTransmiss() { }
    public ReflectivityTransmiss(float rx, float gy, float bz) {
        this.rx = rx;
        this.gy = gy;
        this.bz = bz;
    }

    public static ReflectivityTransmiss fromVector(ReflectivityTransmiss transmiss, Vector3f vec) {
        transmiss.rx = vec.x;
        transmiss.gy = vec.y;
        transmiss.bz = vec.z;
        return transmiss;
    }

    public ReflectivityTransmiss fromVector(Vector3f vec) {
        return fromVector(this, vec);
    }

    public Vector3f toVector() {
        return new Vector3f((float)rx, (float)gy, (float)bz);
    }

    public String toString() {
        return "isRGB(" + isRGB + ") isXYZ(" + isXYZ + ")" + "RX, GY, BZ: (" + rx + ", " + gy + ", " + bz + ")";
    }
}