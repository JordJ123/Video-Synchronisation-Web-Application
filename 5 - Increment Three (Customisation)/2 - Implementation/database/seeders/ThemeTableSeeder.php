<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\Theme;

class ThemeTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        //Default Theme
        $default = Theme::find(1);
        if ($default == null) {
            $default = new Theme;
            $default->id = 1;
        }
        $default->name = "Default";
        $default->foregroundColour = "#3490dc";
        $default->backgroundColour = "#ffffff";
        $default->buttonForegroundColour = "#ffffff";
        $default->buttonBackgroundColour = "#3490dc";
        $default->save();
        

        //Reverse Theme
        $reverse = Theme::find(2);
        if ($reverse == null) {
            $reverse = new Theme;
            $reverse->id = 2;
        }
        $reverse->name = "Default (Reverse)";
        $reverse->foregroundColour = "#ffffff";
        $reverse->backgroundColour = "#3490dc";
        $reverse->buttonForegroundColour = "#3490dc";
        $reverse->buttonBackgroundColour = "#ffffff";
        $reverse->save();    
    }
}
