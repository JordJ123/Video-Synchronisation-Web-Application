<div class="subsection">
    <form wire:submit.prevent="post">
        <p class="messagebox">
            @foreach($messages as $message)
                {{ $message }}<br>
            @endforeach
        </p>
        <input wire:model="textBox" type="text" required>
        <button>Save</button>
    </form>    
</div>   