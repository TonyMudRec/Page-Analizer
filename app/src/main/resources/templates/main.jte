@import hexlet.code.pages.manager.MainPage
@param MainPage mainPage

@template.layout.index(
page = mainPage,
title = "Enter Url Address",
content = @`
    <section>
    <div class="container-fluid bg-dark p-5">
        <div class="row">
            <div class="col-md-10 col-lg-8 mx-auto text-white">
                <h1 class="display-3 mb-0 fw-medium">Анализатор страниц</h1>
                <p class="lead fw-normal">Бесплатно проверяйте сайты на SEO пригодность</p>
                <form action="/urls" method="post" class="rss-form text-body">
                    <div class="row">
                        <div class="col">
                            <div class="form-floating">
                                <input id="url-input" autofocus type="text" required name="url"
                                       class="form-control w-100">
                                <label for="url-input">Ссылка</label>
                            </div>
                        </div>
                        <div class="col-auto">
                            <button type="submit"
                                    class="h-100 btn btn-lg btn-primary px-sm-5 fw-medium">Проверить
                            </button>
                        </div>
                    </div>
                </form>
                <p class="mt-2 mb-0 text-secondary">Пример: https://www.example.com</p>
            </div>
        </div>
    </div>
    </section>
`
)
