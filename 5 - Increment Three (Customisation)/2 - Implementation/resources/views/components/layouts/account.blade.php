<x-layouts.app>

  <div class="border border-dark bg-primary m-5 p-3">
      <a href="{{ route('home') }}">
          <img class="img-fluid mx-auto d-block" src="{{ URL::asset('logo.png') }}"/></a>
      <h1 class="text-secondary text-center">{{ $title }}</h1>
      <x-messages/>
      {{ $slot }}
  </div> 
  
</x-layouts.app>
