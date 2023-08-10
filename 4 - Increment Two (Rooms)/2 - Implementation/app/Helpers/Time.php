<?php

namespace App\Helpers;

class Time
{
    
    public static function getMilliseconds()
    {
        return round(microtime(true) * 1000);
    }

}