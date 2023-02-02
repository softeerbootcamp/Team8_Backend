<template>
    <div class="d-grid gap-2 col-6 mx-auto">
      <div class="card" style="width: 18rem;" v-if="isCardOn">
        <div class="card-body">
          <h5 class="card-title"></h5>
          <p class="card-text"></p>
          <a href="#" class="btn btn-primary">Go somewhere</a>
        </div>
      </div>
    </div>
  </template>
  <script>
  export default {
    props: ['isCardOn'],
  };
  </script>
  <style>
  
  </style>
  
  
  
  