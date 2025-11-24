package com.primaria.app.Service;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.primaria.app.DTO.UsuarioInfoDTO;
import com.primaria.app.Model.Usuario;
import com.primaria.app.repository.UsuarioRepository;

@Service
public class UsuarioService {

    public final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }
    
    public Usuario save(Usuario usuario) {
        // Hashea la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }
  public Optional<Usuario> obtenerPorId(UUID id) {
        return usuarioRepository.findById(id);
    }
    public Optional<Usuario> authenticate(String email, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            // Compara contraseña hasheada
            if (passwordEncoder.matches(password, usuario.getPassword())) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }
    
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }


      public UsuarioInfoDTO obtenerUsuarioInfo(UUID idUsuario) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + idUsuario));

        return new UsuarioInfoDTO(
                usuario.getNombre(),
                usuario.getNombreCompleto(),
                usuario.getRol().name()
        );
    }
}
