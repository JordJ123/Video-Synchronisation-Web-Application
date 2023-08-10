<div class="flex-grow-1">

    <form wire:submit.prevent="post">

        <!-- Messages -->
        <div class="mb-2 p-1 border border-dark text-wrap text-break 
            bg-secondary vh-75" style="overflow-y: scroll;">
            @foreach($messages as $message)

                <div class='row g-0 align-items-center'>

                    <div class="col-auto">
                        <img id="message{{ $message['id'] }}" 
                            src="{{  $message['userAvatar'] }}" 
                            class="rounded-circle img-fluid me-1" alt="" 
                            style="height: 20px; width: 20px; max-height: 20px; 
                                max-width: 20px;">
                    </div>

                    <div class="col-auto">
                        {{ $message['userName'] }}: {{ $message['text'] }}<br>
                    </div>

                </div>

            @endforeach
        </div>

        <!-- Entry Box -->
        <div class="d-flex">
            <input wire:model="textBox" class="form-control my-1 me-1" 
                type="text" maxlength="255" required>
            <button class="my-1"
                style=
                "color:{{ $room->theme()->first()->buttonForegroundColour }};
                background-color:
                    {{ $room->theme()->first()->buttonBackgroundColour }};
                border-color:
                    {{ $room->theme()->first()->buttonBackgroundColour }}">
                Save
            </button>
        </div>
        
    </form>   
    
</div>   