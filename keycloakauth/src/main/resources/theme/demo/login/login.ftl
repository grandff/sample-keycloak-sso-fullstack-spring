<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=social.displayInfo; section>
    <#-- 타이틀 -->
    <#if section = "title">
        ${msg("loginTitle",(realm.displayName!''))}
    <#-- 헤더 -->
    <#elseif section = "header">        
        <script>
            function togglePassword() {
                var x = document.getElementById("password");
                var v = document.getElementById("vi");
                if (x.type === "password") {
                    x.type = "text";
                    v.src = "${url.resourcesPath}/img/eye.png";
                } else {
                    x.type = "password";
                    v.src = "${url.resourcesPath}/img/eye-off.png";
                }
            }

            function goToRegister() {
                var url = new URL(window.location.href);
                var clientId = url.searchParams.get("client_id");                
                location.href = "http://localhost:8083/member/register?clientId=" + clientId;
            }
        </script>
    <#-- form -->
    <#elseif section = "form">        
        <div class="rounded-lg border bg-card text-card-foreground shadow-sm mx-auto max-w-sm p-5" 
        data-v0-t="card"
        style="padding:5rem;">
            <div class="flex flex-col p-6 space-y-1" style="margin-bottom: 5rem;">
                <h3 class="whitespace-nowrap tracking-tight text-2xl font-bold">로그인</h3>
                <p class="text-sm text-muted-foreground">로그인을 하기위한 아이디와 패스워드를 입력해주세요.</p>
            </div>
            <div class="p-6">
                <form id="kc-form-login" class="form" onsubmit="return true;" action="${url.loginAction}" method="post">
                <div class="space-y-4">
                    <div class="space-y-2" style="margin-bottom:2rem;">
                        <label
                        class="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                        for="username"
                        >
                        아이디
                        </label>
                        <input
                        class="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                        id="username"
                        placeholder="아이디를 입력해주세요."
                        required=""
                        type="text"
                        name="username"
                        />
                    </div>
                    <div class="space-y-2" style="margin-bottom:2rem;">
                        <div class="flex items-center">
                        <label
                            class="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                            for="password"
                        >
                            비밀번호
                        </label>
                        <a class="ml-auto inline-block text-sm underline" href="#">
                            비밀번호를 잊으셨나요?
                        </a>
                    </div>
                    <input
                    class="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                    id="password"
                    required=""
                    type="password"
                    name="password"
                    />
                </div>
                <button
                    class="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 bg-primary text-primary-foreground hover:bg-primary/90 h-10 px-4 py-2 w-full"
                    type="submit"
                    style="border: 1px solid black;"
                >
                    로그인
                </button>
                </form>
                <#--  <button class="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2 w-full">
                    Login with Google
                </button>  -->
                </div>
                <div class="mt-4 text-center text-sm" style="margin-top:2rem;">
                회원이 아니신가요?
                <a class="underline" onclick="goToRegister();" href="#" style="
                    font-size: 1.25rem;
                    color: blue;
                    font-weight: bold;
                ">
                    회원가입
                </a>
                </div>
            </div>
        </div>
        <div class="box-container">
            
        <#if realm.password>
            
        </#if>
        <#if social.providers??>
            <p class="para">${msg("selectAlternative")}</p>
            <div id="social-providers">
                <#list social.providers as p>
                <input class="social-link-style" type="button" onclick="location.href='${p.loginUrl}';" value="${p.displayName}"/>
                </#list>
            </div>
        </#if>
        </div>
        <div>
            <p class="copyright">&copy; ${msg("copyright", "${.now?string('yyyy')}")}</p>
        </div>
    </#if>
</@layout.registrationLayout>