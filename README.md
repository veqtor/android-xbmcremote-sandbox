
Sandbox for Next-Generation XBMC-Remote
=======================================

Time has come for a redesign. In a nutshell, these are the major changes:

* New UI. Make it beautiful on phones, tablets and anything in-between, using
  common [Android design patterns](http://developer.android.com/design/index.html).
* New backend. Use JSON-RPC instead of non-existent HTTPAPI, using the
  autogenerated library [here](https://github.com/freezy/xbmc-jsonrpclib-android).
* Faster experience. Use locally cached data for faster access and offline usage.
* More reliable. Make use of the libs out there. Image fetching and caching shouldn't
  be done by hand anymore.
* Easier setup. Use zeroconf where available and system-wide accounts for credential
  storage.

## Installation

    git clone https://github.com/freezy/android-xbmcremote-sandbox.git
    cd android-xbmcremote-sandbox
    git submodule init
    git submodule update


## Status Quo
Still putting the pieces together, so nothing to see yet. There are some mockups of
the new UI [here](https://github.com/freezy/android-xbmcremote-sandbox/tree/master/doc/mockups).
