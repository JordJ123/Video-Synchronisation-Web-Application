<x-layouts.room>

    <!-- Title -->
    <h1 class="text-primary mb-4">
        <b>{{ auth()->user()->name }}'s Settings</b></h1>

    <!-- Change Avatar -->
    <livewire:avatar-change/>

    <!-- Change Password -->
    <livewire:change-password/>

</x-layouts.room>