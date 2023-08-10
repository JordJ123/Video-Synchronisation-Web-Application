<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateRoomUserTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('room_user', function (Blueprint $table) {
            // $table->id();
            $table->primary(['room_id', 'user_id']);
            $table->unsignedBigInteger('room_id');
            $table->unsignedBigInteger('user_id');
            $table->boolean('isModerator')->default(false);
            $table->boolean('isBlocked')->default(false);;
            $table->timestamps();

            $table->foreign('room_id')->references('id')->on('rooms')
                ->onDelete('cascade')->onUpdate('cascade');
            $table->foreign('user_id')->references('id')->on('users')
                ->onDelete('cascade')->onUpdate('cascade');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('room_user');
    }
}
