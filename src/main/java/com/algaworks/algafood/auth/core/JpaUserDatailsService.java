package com.algaworks.algafood.auth.core;

import com.algaworks.algafood.auth.domain.model.Usuario;
import com.algaworks.algafood.auth.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class JpaUserDatailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        return new AuthUser(usuario, getAuthorities(usuario));
    }

    private Collection<GrantedAuthority> getAuthorities(Usuario usuario){
        return usuario.getGrupos().stream()
                .flatMap(grupo -> grupo.getPermissoes().stream())
                .map(permissao -> new SimpleGrantedAuthority(permissao.getNome()))
                .collect(Collectors.toSet());
    }

}
