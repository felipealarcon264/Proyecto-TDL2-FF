package DAO;

import Catalogo.Resenia;
import java.util.List;

public interface ReseniaDAO {
    public boolean guardar(Resenia resenia);
    public boolean borrar(Resenia resenia);
    public Resenia buscarPorId(int id);
    public List<Resenia> devolverListaResenia();
    public boolean actualizar(Resenia resenia);

}
