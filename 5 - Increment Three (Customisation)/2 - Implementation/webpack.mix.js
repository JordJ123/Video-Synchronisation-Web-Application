const mix = require('laravel-mix');

/*
 |--------------------------------------------------------------------------
 | Mix Asset Management
 |--------------------------------------------------------------------------
 |
 | Mix provides a clean, fluent API for defining some Webpack build steps
 | for your Laravel applications. By default, we are compiling the CSS
 | file for the application as well as bundling up all the JS files.
 |
 */

mix.js('resources/js/app.js', 'public/js')
    .sass('resources/sass/app.scss', 'public/css')
    .scripts('resources/js/rooms.index.js', 'public/js/rooms.index.js')
    .scripts('resources/js/rooms.show.js', 'public/js/rooms.show.js')
    .scripts('resources/js/settings-box.js', 'public/js/settings-box.js')
    .scripts('resources/js/video-player.js', 'public/js/video-player.js')
    .sourceMaps();
