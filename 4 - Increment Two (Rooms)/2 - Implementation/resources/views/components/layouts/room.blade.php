<x-layouts.app>

    <style>
        body {
            overflow: hidden;
        }
    </style>    
        
    <div class="d-flex">
    
        <livewire:side-bar :roomCode="$room->code ?? null"/>

        <div class="flex-fill m-3">
            <x-errors/>
            {{ $slot }}
        </div>    
        
    </div>    

</x-layouts.app>
