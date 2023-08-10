<div class="flex-grow-1">

    <form wire:submit.prevent="post">

        <div class="mb-2 p-1 border border-dark text-wrap text-break vh-75" 
            style="overflow-y: scroll;">
            @foreach($messages as $message)
                {{ $message }}<br>
            @endforeach
        </div>

        <div class="d-flex">
            <input wire:model="textBox" class="form-control my-1 me-1" type="text" maxlength="255" 
                required>
            <button class="btn-primary text-secondary my-1">Save</button>
        </div>
        
    </form>   
    
</div>   