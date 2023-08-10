<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Str;
use Illuminate\Support\Facades\Storage;
use Illuminate\Support\Facades\Response;
use Iman\Streamer\VideoStreamer;
use App\Events\Kick;
use App\Models\Room;

class RoomController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index(Request $request)
    {
        if ($request->query('closed') == 1) {
            return redirect()->route('rooms.index')->withErrors(
                ['msg' => "The administrator has closed this room"]);
        } else if ($request->query('kicked') == 1) {
            return redirect()->route('rooms.index')->withErrors(
                ['msg' => "You have been kicked from this room"]);
        } else {
            return view('rooms.index');
        }
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        //Validation
        $validatedData = $request->validate([            
            'name' => 'required|max:16',
        ]);

        //Store Resource
        $room = new Room;
        $room->name = $validatedData['name'];
        $room->code = Str::random(8);
        $room->admin_id = auth()->user()->id;
        do {
            $check = Room::where('code', $room->code)->first();
        } while ($check != null);
        $room->save();

        //Redirect
        return redirect()->route('rooms.show', ['code' => $room->code]);
    }

    /**
     * Store a newly created video in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function storeVideo(Request $request, $code)
    {
        $validatedData = $request->validate([
            'file' => 'required|mimes:mp4',
        ]);
        $request->file->move(storage_path('app/videos'), $code.".mp4");
    }

    /**
     * Display the specified resource.
     *
     * @param  string  $code
     * @return \Illuminate\Http\Response
     */
    public function show($code)
    {
        $room = Room::where('code', $code)->first();
        if ($room != null) {
            $user = $room->users()->find(auth()->user()->id);
            if ($user != null) {
                if ($room->blocked()->find($user->id)) {
                    return redirect()->route('rooms.index')->withErrors(
                        ['msg' => "You have been kicked from this room"]);
                }
            } else {
                auth()->user()->addRoom($room->id);
            }
            return view('rooms.show', ['room' => $room, 'theme' => $room->theme()->first()->backgroundColour]);
        } else {
            return redirect()->route('rooms.index')->withErrors(
                ['msg' => "Room with this code does not exist"]);
        }
    }

    /**
     * Display the specified resource's video.
     *
     * @param  string  $code
     * @return \Illuminate\Http\Response
     */
    public function showVideo($code)
    {
        $video = storage_path('app/videos/' . $code . '.mp4');
        VideoStreamer::streamFile($video);
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        //
    }

    /**
     * Remove the specified resource's video from storage.
     *
     * @param  String  $code
     * @return \Illuminate\Http\Response
     */
    public function destroyVideo($code)
    {
        Storage::delete('videos/' . $code . '.mp4');
    }
}
