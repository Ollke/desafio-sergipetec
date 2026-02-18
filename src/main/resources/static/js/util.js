function submeterFormulario(caminho, tipo){
    let form = document.createElement('form');
    form.method = tipo;
    form.action = caminho;

    let input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'lastLink';
    input.value = window.location.href;

    form.appendChild(input);
    
    try {
        let inputsArray = Array.from(document.querySelectorAll("input"));
        for (let input of inputsArray) {

            if(input.type == 'radio' && validarRadio(input.name)){
                form.appendChild(input);
            }else{
                form.appendChild(input);
            }
        }

        inputsArray = Array.from(document.querySelectorAll("select"));
        for (let select of inputsArray) {            
            let input = document.createElement('input');
            input.type = 'hidden';
            input.name = select.name;
            input.value = select.value;

            form.appendChild(input);
        }
    }catch {

    }
    
    document.body.appendChild(form);
    form.submit();
}

function validarRadio(name, exibirAlert){
    const radio = document.querySelector('input[name="'+ name +'"]:checked')

    if(radio == null){
        if(exibirAlert)
            alert("Selecione um registro")
        return false;
    }

     return true;
}