function submeterFormulario(caminho, tipo){
    let form = document.createElement('form');
    form.method = tipo;
    form.action = caminho;

    let input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'lastLink';
    input.value = window.location.href;

    form.appendChild(input);

    if (window.opener) {
        let inputAux = document.createElement('input');
        inputAux.type = 'hidden';
        inputAux.name = 'auxiliar';
        inputAux.value = 's';

        form.appendChild(inputAux);
    }
    
    try {
        let componentes = Array.from(document.querySelectorAll("input"));
        for (let componente of componentes) {
            if(componente.type == 'radio' && !validarRadio(componente.name)){
                continue;
            }

            let input = document.createElement('input');
            input.type = 'hidden';
            input.name = componente.name;
            input.value = componente.value;

            form.appendChild(input);
        }

        componentes = Array.from(document.querySelectorAll("select"));
        componentes = componentes.concat( Array.from(document.querySelectorAll("textarea")) );
        for (let componente of componentes) {            
            let input = document.createElement('input');
            input.type = 'hidden';
            input.name = componente.name;
            input.value = componente.value;

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
            exibirMensagem("Selecione um registro")
        return false;
    }

     return true;
}

async function exibirMensagem(msg){
    Swal.fire({
        text: msg,
        icon: 'info',
        confirmButtonText: 'OK'
    });
}


async function solicitarConfirmacao(msg) { 
    var resultado = await Swal.fire({
        text: msg,
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Confirmar',
        cancelButtonText: 'Cancelar'
    });
 
    return resultado.isConfirmed;
}

function abrirJanelaAuxiliar(rota){
    window.open(rota, 'janelaAuxiliar', 'width=1500,height=800,resizable=yes,scrollbars=yes');
}