<template>
    <div class="d-grid gap-2 col-6 mx-auto">

      <div class="card" style="width: 18rem;">
        <div class="card-body">
          <div class="card-title">코스 정보</div>
          <p class="card-text">{{courseData}}!!!</p>
          <a href="#" class="btn btn-primary">Go somewhere</a>
        </div>
      </div>
    </div>
  </template>
  <script>
  export default {
    props: ['subjectDetail'],
    data(){
        return{
            courseData : this.props

        }
    }

  };
  </script>
  <style>
  
  </style>
  
  
  
  