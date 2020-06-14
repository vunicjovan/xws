<template>
  <div class="home">
    <i class="far fa-registered fa-10x" aria-hidden="true" style="color:#00cc6a"></i>
    <HelloWorld/>
    <AdvertisementList></AdvertisementList>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import AdvertisementList from '@/views/AdvertisementList.vue'

export default {
  name: 'Home',
  components: {
    HelloWorld,
    AdvertisementList
  }
}
</script>
