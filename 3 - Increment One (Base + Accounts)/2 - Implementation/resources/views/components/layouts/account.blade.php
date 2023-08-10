<x-layouts.app>
    
  <div class="w-50 position-absolute top-50 start-50 translate-middle border border-dark bg-primary 
      p-3">
      <h1 class="text-secondary text-center">{{ $title }}</h1>
      {{ $slot }}
  </div>

</x-layouts.app>
