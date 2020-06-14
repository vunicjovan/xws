<template>
  <div id="app">
    <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/postAd">Post advertisement</router-link>
      <router-link to="/abouta">Chat</router-link>
    </div>
    <div id="body">
			<router-view />
		</div>
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #486685;
  padding: 0.75%;
}

#nav a.router-link-exact-active {
  color: #42b983;
}

#body {
	font-family: Avenir, Helvetica, Arial, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	padding: 15px;
	color: white;
}
</style>
