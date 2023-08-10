<?php

namespace App\Http\Controllers\Auth;

use App\Http\Controllers\Controller;
use App\Providers\RouteServiceProvider;
use Illuminate\Http\Request;

class VerificationPromptController extends Controller
{
    /**
     * Display the  verification prompt.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return mixed
     */
    public function __invoke(Request $request)
    {
        return $request->user()->hasVerified()
                    ? redirect()->intended(RouteServiceProvider::HOME)
                    : view('auth.verify-');
    }
}
