package br.com.fiap.techchallengefase2.infra.gateway;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.usuario.CategoriaUsuario;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.usuario.UsuarioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.RestauranteRepository;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.TipoUsuarioRepository;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UsuarioGatewayImpl implements UsuarioGateway {

    private final UsuarioRepository repository;
    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final RestauranteRepository restauranteRepository;

    @Override
    public Optional<UsuarioBase> buscarPorId(Long usuarioId) {
        return repository.findById(usuarioId).map(this::toDomain);
    }

    @Override
    public void deletarPorId(Long usuarioId) {
        repository.deleteById(usuarioId);
    }

    @Override
    public UsuarioBase salvar(UsuarioBase usuario) {
        UsuarioEntityJPA entity = toEntity(usuario);
        UsuarioEntityJPA savedEntity = repository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public UsuarioBase atualizarSenha(String senhaCodificada, Long usuarioId) {
        UsuarioEntityJPA entity = repository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        entity.setSenha(senhaCodificada);
        UsuarioEntityJPA savedEntity = repository.save(entity);

        return toDomain(savedEntity);
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
        return repository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private UsuarioBase toDomain(UsuarioEntityJPA entity) {
        List<TipoUsuario> tipos = tipoUsuarioRepository.findAllByUsuarioId(entity.getUsuarioId()).stream()
                .map(t -> new TipoUsuario(t.getTipoUsuarioId(), t.getRestauranteId(), t.getNome()))
                .toList();

        if (entity.getCategoria() == CategoriaUsuario.DONO) {
            // Busca os restaurantes onde este usuário é o dono
            List<Restaurante> restaurantes = restauranteRepository.findAllByUsuarioId(entity.getUsuarioId()).stream()
                    .map(r -> new Restaurante(
                            r.getRestauranteId(),
                            r.getNome(),
                            r.getEndereco(),
                            r.getTipoCozinha(),
                            r.getHorarioFuncionamento(),
                            r.getUsuarioId()))
                    .toList();

            return new Dono(
                    entity.getUsuarioId(),
                    entity.getNome(),
                    entity.getEmail(),
                    entity.getLogin(),
                    entity.getSenha(),
                    entity.getEndereco(),
                    restaurantes,
                    tipos
            );
        } else {
            return new Cliente(
                    entity.getUsuarioId(),
                    entity.getNome(),
                    entity.getEmail(),
                    entity.getLogin(),
                    entity.getSenha(),
                    entity.getEndereco(),
                    tipos
            );
        }
    }

    private UsuarioEntityJPA toEntity(UsuarioBase domain) {
        return UsuarioEntityJPA.builder()
                .usuarioId(domain.getUsuarioId())
                .nome(domain.getNome())
                .endereco(domain.getEndereco())
                .email(domain.getEmail())
                .login(domain.getLogin())
                .senha(domain.getSenha())
                .categoria(domain.getCategoriaUsuario() == 0 ? CategoriaUsuario.DONO : CategoriaUsuario.CLIENTE)
                .build();
    }
}