package br.com.fiap.techchallengefase2.infra.gateway.jpa;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.usuario.UsuarioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.mapper.UsuarioMapperJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UsuarioGatewayJPA implements UsuarioGateway {
    private final UsuarioRepository repository;
    private final UsuarioMapperJPA usuarioMapperJPA;

    @Override
    public Optional<UsuarioBase> buscarPorId(Long usuarioId) {
        return repository.findByIdWithRelacionamentos(usuarioId).map(usuarioMapperJPA::toDomain);
    }

    @Override
    public void deletarPorId(Long usuarioId) {
        repository.deleteById(usuarioId);
    }

    @Override
    public UsuarioBase salvar(UsuarioBase usuario) {
        UsuarioEntityJPA entity = usuarioMapperJPA.toEntity(usuario);
        UsuarioEntityJPA savedEntity = repository.save(entity);
        return usuarioMapperJPA.toDomain(savedEntity);
    }

    @Override
    public boolean existeUsuarioComLogin(String login) {
        return repository.findByLogin(login).isPresent();
    }

    @Override
    public boolean existeUsuarioComEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }

    @Override
    public Collection<UsuarioBase> buscarTodos() {
        return repository.findAllWithRelacionamentos().stream()
                .map(usuarioMapperJPA::toDomain)
                .collect(Collectors.toList());
    }


}