<template>
    <div class="container">
        <Header />
        <h1>Welcome {{ username }} !</h1>
    </div>
</template>

<script>
import Header from '../components/Header'

export default {
    name: 'MyPage',
    components: {
        Header,
    },
    props: {
        username: String
    }
}
</script>

<style scoped>
.container {
  background-color: rgb(80, 80, 80);
  background-image: url("../assets/security_img.jpg");
  background-repeat: no-repeat;
  background-size: cover;
  color: white;
  width: 60%;
  height: 440px;
  padding: 30px;
  font-family: 'Montserrat', sans-serif;
  margin: auto;
  margin-top: 100px;
  border-radius: 20px;
}

h1 {
    margin-top: 150px;
    color: dodgerblue;
}
</style>