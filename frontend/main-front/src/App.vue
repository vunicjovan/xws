<template>
  <div id="app">
    <div id="nav">
      <Toolbar />
    </div>
    <div id="body">
      <router-view/>
    </div>
  </div>
</template>

<script>
export default {
  components: {
    Toolbar: () => import("./components/navigation/Toolbar.vue")
  }
}
</script>

<style>
#body {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  padding: 15px;
  color: #2c3e50;
}
</style>
