<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\RoomController;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

//home
Route::get('/', function () {
    return view('home');
})->middleware(['guest']);

//rooms
Route::get('/rooms', [RoomController::class, 'index'])
    ->middleware(['auth'])->name('rooms.index');
Route::post('/rooms', [RoomController::class, 'store'])
    ->middleware(['auth'])->name('rooms.store');

//rooms/{id}
Route::get('/rooms/{code}', [RoomController::class, 'show'])
    ->middleware(['auth'])->name('rooms.show');
Route::post('/rooms/{code}', [RoomController::class, 'storeVideo'])
    ->middleware(['auth'])->name('rooms.storeVideo');

require __DIR__.'/auth.php';
