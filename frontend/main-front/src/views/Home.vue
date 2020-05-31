<template>
	<div class="home">
		<HelloWorld msg="Welcome to Your Vue.js App" />
		<!-- <AdvertismentForm /> -->
	</div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from "@/components/HelloWorld.vue";
// import AdvertismentForm from "@/components/forms/AdvertismentForm.vue";

export default {
	name: "Home",
	components: {
		HelloWorld,
		// AdvertismentForm,
	},
};
</script>
