'use strict';
const CACHE_NAME = 'flutter-app-cache';
const RESOURCES = {
  "favicon.png": "5dcef449791fa27946b3d35ad8803796",
"manifest.json": "d17bb16178e92102170ac48b1860a029",
"icons/Icon-512.png": "96e752610906ba2a93c65f8abe1645f1",
"icons/Icon-192.png": "ac9a721a12bbc803b44f645561ecb1e1",
"assets/images/i_am_rich_app_icon.png": "7ee5be83bbadb20fcab98ecd828f68f3",
"assets/images/playstore.png": "77f6c1adc757d4948b24cf9be50f23ec",
"assets/images/diamond.png": "23619afbfd004dfd2901de08a9320415",
"assets/images/appstore.png": "a1c4f5f50bb0ca2b79c0cadcb666d2d0",
"assets/AssetManifest.json": "72cbc99ab5398fffe51e2749f50d57e4",
"assets/FontManifest.json": "01700ba55b08a6141f33e168c4a6c22f",
"assets/packages/cupertino_icons/assets/CupertinoIcons.ttf": "115e937bb829a890521f72d2e664b632",
"assets/LICENSE": "0000d255865246c9b55862d96ffd3089",
"assets/fonts/MaterialIcons-Regular.ttf": "56d3ffdef7a25659eab6a68a3fbfaf16",
"main.dart.js": "f756fdb57d4e9d8bfe3880c5038cd660",
"index.html": "b80e11424400851c1a4a7dd67d0f0ef3",
"/": "b80e11424400851c1a4a7dd67d0f0ef3"
};

self.addEventListener('activate', function (event) {
  event.waitUntil(
    caches.keys().then(function (cacheName) {
      return caches.delete(cacheName);
    }).then(function (_) {
      return caches.open(CACHE_NAME);
    }).then(function (cache) {
      return cache.addAll(Object.keys(RESOURCES));
    })
  );
});

self.addEventListener('fetch', function (event) {
  event.respondWith(
    caches.match(event.request)
      .then(function (response) {
        if (response) {
          return response;
        }
        return fetch(event.request);
      })
  );
});
