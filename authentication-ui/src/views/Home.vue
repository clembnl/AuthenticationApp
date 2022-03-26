<template>
  <div class="container">
    <Header />
    <h1>{{ title }}</h1>
    <div id="option">
      <h2>Or <span @click="onClick()" class="click">{{ option }}</span></h2>
    </div>
    <SignInForm v-show="showSignIn" @signin="signIn" />
    <SignUpForm v-show="showSignUp" @signup="signUp"/>
    <ResetButton />
  </div>
</template>

<script>
import Header from "../components/Header"
import SignInForm from "../components/SignInForm"
import SignUpForm from "../components/SignUpForm"
import axios from "axios"
import swal from "sweetalert"

export default {
  name: 'Home',
  components: {
    Header,
    SignInForm,
    SignUpForm,
  },
  data() {
    return {
      title: 'Login',
      option: 'Register',
      showSignIn: true,
      showSignUp: false,
    }
  },
  methods: {
    async signIn(user) {
      await axios
        .post('api/users/signin', user)
        .then(() => {
          this.username = user.username
          this.$router.push({name: "MyPage", params: {username: this.username}})
          swal({
            text: "User signin successful",
            icon: "success",
          });
        })
        .catch((err) => console.log("err", err));
    },
    async signUp(user) {
      await axios
        .post('api/users/signup', user)
        .then(() => {
          this.$router.push({name: "MyPage", params: {username: this.username}})
          swal({
            text: "User signup successful",
            icon: "success",
          });
        })
        .catch((err) => console.log("err", err));
    },    
    onClick() {
      this.showSignIn = !this.showSignIn
      this.showSignUp = !this.showSignUp
      if (this.showSignUp) {
        this.title = 'Register'
        this.option = 'Login'
      }
      else {
        this.title = 'Login'
        this.option = 'Register'        
      }
    }
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
  font-family: 'Poppins', sans-serif;
  margin-top: 30px;
  margin-bottom: 0;
  font-size: 2.1em;
}

h2 {
  font-family: 'Montserrat', sans-serif;
  font-size: 1em;
  font-style: italic;
  margin: 0;
}

.click {
  color: dodgerblue;
  font-size: 1.1em;
  font-weight: bold;
}
</style>