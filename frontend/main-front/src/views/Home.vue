<template>
	<div class="home">
		<HelloWorld msg="Welcome to Your Vue.js App" />
		<StandardView />
	</div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from "@/components/HelloWorld.vue";
import StandardView from "@/views/advertisement/StandardView.vue";

export default {
	name: "Home",
	components: {
		HelloWorld,
		StandardView
	},
};
</script>
