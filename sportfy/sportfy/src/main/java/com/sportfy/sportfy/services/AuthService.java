package com.sportfy.sportfy.services;

import com.sportfy.sportfy.dtos.DadosAuthDto;
import com.sportfy.sportfy.dtos.DadosTokenJwtDto;
import com.sportfy.sportfy.models.Usuario;
import com.sportfy.sportfy.repositories.UsuarioRepository;
import com.sportfy.sportfy.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService implements UserDetailsService {

    private AuthenticationManager manager;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public DadosTokenJwtDto efetuarLogin(@RequestBody @Valid DadosAuthDto dados) {
        manager = context.getBean(AuthenticationManager.class);
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.username(), dados.password());
        var authentication = this.manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        System.out.println("Token gerado: " + tokenJWT);
        return new DadosTokenJwtDto(tokenJWT);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails usuario = usuarioRepository.findByUsernameAndAtivo(username, true);
        if (usuario != null){
            return usuario;
        }else {
            throw new UsernameNotFoundException("Usuario não encontrado!");
        }
    }
    
}
