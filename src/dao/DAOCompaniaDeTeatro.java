package dao;

import vos.CompaniaDeTeatro;
import vos.Usuario;
import vos.UsuarioRegistrado;
import vos.reportes.RFC11;
import vos.reportes.RFC5;
import vos.reportes.RFC8;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DAOCompaniaDeTeatro extends DAO
{
	public DAOCompaniaDeTeatro( )
	{
		super( );
	}
	
	public CompaniaDeTeatro createCompaniaDeTeatro( CompaniaDeTeatro object ) throws SQLException
	{
		StringBuilder sql = new StringBuilder( );
		sql.append( "INSERT INTO COMPANIAS_DE_TEATRO " );
		sql.append( "( id, tipo_id, nombre, nombre_representante, pais_origen, pagina_web, fecha_llegada, fecha_salida ) " );
		sql.append( "VALUES " );
		sql.append( "( " );
		sql.append( String.format( "%s, ", object.getIdentificacion( ) ) );
		sql.append( String.format( "'%s', ", object.getTipoIdentificacion( ) ) );
		sql.append( String.format( "'%s', ", object.getNombre( ) ) );
		sql.append( String.format( "'%s', ", object.getNombreRepresentante( ) ) );
		sql.append( String.format( "'%s', ", object.getPaisOrigen( ) ) );
		sql.append( String.format( "'%s', ", object.getPaginaWeb( ) ) );
		sql.append( String.format( "%s, ", toDate( object.getFechaLlegada( ) ) ) );
		sql.append( String.format( "%s ", toDate( object.getFechaSalida( ) ) ) );
		sql.append( ")" );
		
		PreparedStatement s = connection.prepareStatement( sql.toString( ) );
		recursos.add( s );
		s.execute( );
		s.close( );
		return object;
	}
	
	public List<CompaniaDeTeatro> getCompaniaDeTeatros( ) throws SQLException
	{
		List<CompaniaDeTeatro> list = new ArrayList<>( );
		
		StringBuilder sql = new StringBuilder( );
		sql.append( "SELECT * FROM COMPANIAS_DE_TEATRO CT INNER JOIN USUARIOS U" );
		sql.append( "                                      ON CT.id = U.identificacion " );
		sql.append( "                                      AND CT.tipo_id = U.tipo_identificacion " );
		
		PreparedStatement s = connection.prepareStatement( sql.toString( ) );
		recursos.add( s );
		ResultSet rs = s.executeQuery( );
		while( rs.next( ) )
		{
			list.add( resultToCompaniaDeTeatro( rs ) );
		}
		
		rs.close( );
		s.close( );
		return list;
	}
	
	public CompaniaDeTeatro getCompaniaDeTeatro( Long id ) throws SQLException
	{
		CompaniaDeTeatro object = null;
		
		StringBuilder sql = new StringBuilder( );
		sql.append( "SELECT * " );
		sql.append( "FROM COMPANIAS_DE_TEATRO CT INNER JOIN USUARIOS U " );
		sql.append( "                            ON CT.id = U.identificacion " );
		sql.append( String.format( "WHERE id = %s ", id ) );
		sql.append( String.format( "AND tipo_id = '%s'", CompaniaDeTeatro.TIPO_ID ) );
		
		PreparedStatement s = connection.prepareStatement( sql.toString( ) );
		recursos.add( s );
		ResultSet rs = s.executeQuery( );
		if( rs.next( ) )
		{
			object = resultToCompaniaDeTeatro( rs );
		}
		rs.close( );
		s.close( );
		return object;
	}
	
	public CompaniaDeTeatro updateCompaniaDeTeatro( Long idCompania, CompaniaDeTeatro object ) throws SQLException
	{
		StringBuilder sql = new StringBuilder( );
		sql.append( "UPDATE COMPANIAS_DE_TEATRO " );
		sql.append( "SET " );
		sql.append( String.format( "nombre = '%s', ", object.getNombre( ) ) );
		sql.append( String.format( "nombre_representante = '%s', ", object.getNombreRepresentante( ) ) );
		sql.append( String.format( "pagina_web = '%s', ", object.getPaginaWeb( ) ) );
		sql.append( String.format( "pais_origen = '%s', ", object.getPaisOrigen( ) ) );
		sql.append( String.format( "fecha_llegada = %s, ", toDate( object.getFechaLlegada( ) ) ) );
		sql.append( String.format( "fecha_salida = %s ", toDate( object.getFechaSalida( ) ) ) );
		sql.append( String.format( "WHERE id = %s ", idCompania ) );
		sql.append( String.format( "AND tipo_id = '%s'", CompaniaDeTeatro.TIPO_ID ) );
		
		PreparedStatement s = connection.prepareStatement( sql.toString( ) );
		recursos.add( s );
		s.execute( );
		s.close( );
		return object;
	}
	
	public void deleteCompaniaDeTeatro( Long id ) throws SQLException
	{
		StringBuilder sql = new StringBuilder( );
		sql.append( "DELETE FROM COMPANIAS_DE_TEATRO " );
		sql.append( String.format( "WHERE id = %s ", id ) );
		sql.append( String.format( "AND tipo_id = '%s'", CompaniaDeTeatro.TIPO_ID ) );
		
		PreparedStatement s = connection.prepareStatement( sql.toString( ) );
		recursos.add( s );
		s.execute( );
		s.close( );
	}
	
	public List<RFC5> rentabilidad( )
	{
		List<RFC5> list = new LinkedList<>( );
		//TODO RFC5
		return list;
	}
	
	public static CompaniaDeTeatro resultToCompaniaDeTeatro( ResultSet rs ) throws SQLException
	{
		CompaniaDeTeatro object = new CompaniaDeTeatro( );
		object.setIdentificacion( rs.getLong( "id" ) );
		object.setTipoIdentificacion( rs.getString( "tipo_id" ) );
		object.setNombreRepresentante( rs.getString( "nombre_representante" ) );
		object.setPaginaWeb( rs.getString( "pagina_web" ) );
		object.setPaisOrigen( rs.getString( "pais_origen" ) );
		object.setFechaLlegada( rs.getDate( "fecha_llegada" ) );
		object.setFechaSalida( rs.getDate( "fecha_salida" ) );
		
		object.setEmail( rs.getString( "email" ) );
		object.setPassword( rs.getString( "password" ) );
		object.setRol( rs.getString( "rol" ) );
		object.setIdFestival( rs.getLong( "id_festival" ) );
		object.setNombre( rs.getString( "nombre" ) );
		object.setImagen( rs.getString( "imagen" ) );
		return object;
	}
	
	public RFC8 informacionCompania( Long idCompania ) throws SQLException
	{
		StringBuilder sql = new StringBuilder( );
		
		sql.append( "SELECT " );
		sql.append( "  ID_COMPANIA, " );
		sql.append( "  NOMBRE_COMPANIA, " );
		sql.append( "  ID_ESPECTACULO, " );
		sql.append( "  NOMBRE_ESPECTACULO, " );
		sql.append( "  FECHA_FUNCION, " );
		sql.append( "  LUGAR_FUNCION, " );
		sql.append( "  ASISTENCIA * 100 / TOTAL_CAPACIDAD AS OCUPACION, " );
		sql.append( "  ASISTENCIA, " );
		sql.append( "  ASISTENCIA_REGISTRADOS, " );
		sql.append( "  TOTAL " );
		sql.append( "FROM (SELECT " );
		sql.append( "        E.ID          AS ID_ESPECTACULO, " );
		sql.append( "        E.NOMBRE      AS NOMBRE_ESPECTACULO, " );
		sql.append( "        CT.ID         AS ID_COMPANIA, " );
		sql.append( "        U.NOMBRE     AS NOMBRE_COMPANIA, " );
		sql.append( "        F.FECHA       AS FECHA_FUNCION, " );
		sql.append( "        F.ID_LUGAR    AS LUGAR_FUNCION, " );
		sql.append( "        COUNT(*)      AS ASISTENCIA, " );
		sql.append( "        SUM(CL.COSTO) AS TOTAL " );
		sql.append( "      FROM " );
		sql.append( "        BOLETAS B " );
		sql.append( "        INNER JOIN " );
		sql.append( "        USUARIOS U ON U.IDENTIFICACION = B.ID_USUARIO AND B.ID_TIPO = U.TIPO_IDENTIFICACION " );
		sql.append( "        INNER JOIN " );
		sql.append( "        COSTO_LOCALIDAD CL ON B.FECHA = CL.FECHA AND B.ID_LUGAR = CL.ID_LUGAR AND B.ID_LOCALIDAD = CL.ID_LOCALIDAD " );
		sql.append( "        INNER JOIN " );
		sql.append( "        LUGARES L ON L.ID = B.ID_LUGAR " );
		sql.append( "        INNER JOIN " );
		sql.append( "        FUNCIONES F ON F.FECHA = B.FECHA AND F.ID_LUGAR = B.ID_LUGAR " );
		sql.append( "        INNER JOIN " );
		sql.append( "        ESPECTACULOS E ON F.ID_ESPECTACULO = E.ID " );
		sql.append( "        INNER JOIN " );
		sql.append( "        OFRECE O ON O.ID_ESPECTACULO = E.ID " );
		sql.append( "        RIGHT JOIN " );
		sql.append( "        COMPANIAS_DE_TEATRO CT ON O.ID_COMPANIA_DE_TEATRO = CT.ID " );
		sql.append( "        INNER JOIN " );
		sql.append( "        USUARIOS U ON U.IDENTIFICACION = CT.ID AND U.TIPO_IDENTIFICACION = CT.TIPO_ID " );
		sql.append( String.format( "WHERE CT.ID = %s ", idCompania ) );
		sql.append( "      GROUP BY E.ID, E.NOMBRE, CT.ID, U.NOMBRE, F.FECHA, F.ID_LUGAR " );
		sql.append( "      ORDER BY E.ID) Z " );
		sql.append( "  LEFT JOIN (SELECT " );
		sql.append( "               CL.FECHA, " );
		sql.append( "               CL.ID_LUGAR, " );
		sql.append( "               COUNT(*) AS ASISTENCIA_REGISTRADOS " );
		sql.append( "             FROM BOLETAS B " );
		sql.append( "               INNER JOIN COSTO_LOCALIDAD CL ON B.FECHA = CL.FECHA AND B.ID_LUGAR = CL.ID_LUGAR AND B.ID_LOCALIDAD = CL.ID_LOCALIDAD " );
		sql.append( "               INNER JOIN USUARIOS U ON B.ID_USUARIO = U.IDENTIFICACION AND B.ID_TIPO = U.TIPO_IDENTIFICACION " );
		sql.append( String.format( "   WHERE U.ROL = '%s' ", Usuario.USUARIO_REGISTRADO ) );
		sql.append( "             GROUP BY CL.FECHA, CL.ID_LUGAR, U.ROL) B ON Z.FECHA_FUNCION = B.FECHA AND Z.LUGAR_FUNCION = B.ID_LUGAR " );
		sql.append( "  LEFT JOIN (SELECT " );
		sql.append( "               L.ID, " );
		sql.append( "               SUM(CAPACIDAD) AS TOTAL_CAPACIDAD " );
		sql.append( "             FROM LUGARES L " );
		sql.append( "               INNER JOIN LUGAR_LOCALIDAD LL ON L.ID = LL.ID_LUGAR " );
		sql.append( "             GROUP BY L.ID) C ON C.ID = Z.LUGAR_FUNCION " );
		sql.append( String.format( "WHERE ID_COMPANIA = %s ", idCompania ) );
		
		System.out.println( sql.toString( ) );
		
		PreparedStatement s = connection.prepareStatement( sql.toString( ) );
		recursos.add( s );
		ResultSet rs = s.executeQuery( );
		
		RFC8 rfc8 = new RFC8( );
		List<RFC8.EspectaculoInfo> list = new LinkedList<>( );
		Long idActual = -1L;
		
		boolean move = false;
		while( move || rs.next( ) )
		{
			move = true;
			
			if( rfc8.getIdCompania( ) == null )
			{
				rfc8.setIdCompania( rs.getLong( "ID_COMPANIA" ) );
				rfc8.setNombreCompania( rs.getString( "NOMBRE_COMPANIA" ) );
			}
			
			RFC8.EspectaculoInfo aux = rfc8.new EspectaculoInfo( );
			List<RFC8.FuncionOcupacion> temp = new LinkedList<>( );
			aux.setNombreespectaculo( rs.getString( "NOMBRE_ESPECTACULO" ) );
			idActual = idActual == -1 ? rs.getLong( "ID_ESPECTACULO" ) : idActual;
			
			while( rs.getLong( "ID_ESPECTACULO" ) == ( idActual ) )
			{
				RFC8.FuncionOcupacion fo = rfc8.new FuncionOcupacion( );
				fo.setAsistencia( aux.getNombreEspectaculo( ) == null ? 0 : rs.getInt( "ASISTENCIA" ) );
				fo.setAsistencia_registrados( rs.getInt( "ASISTENCIA_REGISTRADOS" ) );
				fo.setFecha( rs.getDate( "FECHA_FUNCION" ) );
				fo.setIdFuncion( rs.getLong( "LUGAR_FUNCION" ) );
				fo.setPorcentaje( rs.getDouble( "OCUPACION" ) );
				fo.setTotalPlata( rs.getDouble( "TOTAL" ) );
				temp.add( fo );
				if( !rs.next( ) )
				{
					move = false;
					break;
				}
			}
			
			aux.setOcupaciones( temp );
			Integer asistencia = 0;
			Integer asistenciaRegistrados = 0;
			Double plataTotal = 0d;
			
			for( RFC8.FuncionOcupacion fo : temp )
			{
				asistencia += fo.getAsistencia( );
				asistenciaRegistrados += fo.getAsistencia_registrados( );
				plataTotal += fo.getTotalPlata( );
			}
			aux.setAsistencia( asistencia );
			aux.setAsistenciaRegistrados( asistenciaRegistrados );
			aux.setDinero( plataTotal );
			list.add( aux );
			if( move )
			{
				idActual = rs.getLong( "ID_ESPECTACULO" );
			}
		}
		rfc8.setEspectaculos( list );
		return rfc8;
	}
	
	public List<UsuarioRegistrado> rfc9( Long idCompania, Date fInicio, Date fEnd ) throws SQLException
	{
		List<UsuarioRegistrado> list = new LinkedList<>( );
		
		StringBuilder sql = new StringBuilder( );
		sql.append( "SELECT U.*, UR.* " );
		sql.append( "FROM " );
		sql.append( "  FUNCIONES F " );
		sql.append( "  INNER JOIN " );
		sql.append( "  ESPECTACULOS E " );
		sql.append( "    ON F.ID_ESPECTACULO = E.ID " );
		sql.append( "  INNER JOIN " );
		sql.append( "  OFRECE O " );
		sql.append( "    ON O.ID_ESPECTACULO = E.ID " );
		sql.append( "  INNER JOIN " );
		sql.append( "  COMPANIAS_DE_TEATRO CT " );
		sql.append( "    ON O.ID_COMPANIA_DE_TEATRO = CT.ID AND O.TIPO_ID = CT.TIPO_ID " );
		sql.append( "  INNER JOIN " );
		sql.append( "  BOLETAS B " );
		sql.append( "    ON B.ID_LUGAR = F.ID_LUGAR AND B.FECHA = F.FECHA " );
		sql.append( "  INNER JOIN " );
		sql.append( "  USUARIOS U " );
		sql.append( "    ON B.ID_USUARIO = U.IDENTIFICACION AND B.ID_TIPO = U.TIPO_IDENTIFICACION " );
		sql.append( "  INNER JOIN " );
		sql.append( "  USUARIOS_REGISTRADOS UR " );
		sql.append( "    ON U.IDENTIFICACION = UR.ID_USUARIO AND U.TIPO_IDENTIFICACION = UR.TIPO_ID " );
		sql.append( String.format( "WHERE CT.ID = %s ", idCompania ) );
		sql.append( String.format( "  AND BETWEEN F.FECHA BETWEEN %s AND %s ", toDateTime( fInicio ), toDateTime( fEnd ) ) );
		
		PreparedStatement s = connection.prepareStatement( sql.toString( ) );
		ResultSet rs = s.executeQuery( );
		
		while( rs.next( ) )
		{
			list.add( DAOUsuarioRegistrado.restultToAccesibildiad( rs ) );
		}
		
		rs.close( );
		s.close( );
		return list;
	}
	
	public List<UsuarioRegistrado> rfc10( Long idCompania, Date fInicio, Date fEnd ) throws SQLException
	{
		List<UsuarioRegistrado> list = new LinkedList<>( );
		
		StringBuilder sql = new StringBuilder( );
		sql.append( "SELECT * " );
		sql.append( "FROM USUARIOS " );
		sql.append( "  INNER JOIN USUARIOS_REGISTRADOS UR " );
		sql.append( "    ON USUARIOS.IDENTIFICACION = UR.ID_USUARIO AND USUARIOS.TIPO_IDENTIFICACION = UR.TIPO_ID " );
		sql.append( "MINUS ( SELECT " );
		sql.append( "          U.*, " );
		sql.append( "          UR.* " );
		sql.append( "        FROM " );
		sql.append( "          FUNCIONES F " );
		sql.append( "          INNER JOIN " );
		sql.append( "          ESPECTACULOS E " );
		sql.append( "            ON F.ID_ESPECTACULO = E.ID " );
		sql.append( "          INNER JOIN " );
		sql.append( "          OFRECE O " );
		sql.append( "            ON O.ID_ESPECTACULO = E.ID " );
		sql.append( "          INNER JOIN " );
		sql.append( "          COMPANIAS_DE_TEATRO CT " );
		sql.append( "            ON O.ID_COMPANIA_DE_TEATRO = CT.ID AND O.TIPO_ID = CT.TIPO_ID " );
		sql.append( "          INNER JOIN " );
		sql.append( "          BOLETAS B " );
		sql.append( "            ON B.ID_LUGAR = F.ID_LUGAR AND B.FECHA = F.FECHA " );
		sql.append( "          INNER JOIN " );
		sql.append( "          USUARIOS U " );
		sql.append( "            ON B.ID_USUARIO = U.IDENTIFICACION AND B.ID_TIPO = U.TIPO_IDENTIFICACION " );
		sql.append( "          INNER JOIN " );
		sql.append( "          USUARIOS_REGISTRADOS UR " );
		sql.append( "            ON U.IDENTIFICACION = UR.ID_USUARIO AND U.TIPO_IDENTIFICACION = UR.TIPO_ID " );
		sql.append( String.format( "WHERE CT.ID = %s ", idCompania ) );
		sql.append( String.format( "  AND BETWEEN F.FECHA BETWEEN %s AND %s )", toDateTime( fInicio ), toDateTime( fEnd ) ) );
		
		PreparedStatement s = connection.prepareStatement( sql.toString( ) );
		ResultSet rs = s.executeQuery( );
		
		while( rs.next( ) )
		{
			list.add( DAOUsuarioRegistrado.restultToAccesibildiad( rs ) );
		}
		
		rs.close( );
		s.close( );
		return list;
	}
	
	public List<RFC11> rfc11( String localidad, List<String> requerimientosTecnicos, Date hInicio, Date hFin, Date fInicio, Date fEnd ) throws SQLException
	{
		HashMap<String, RFC11> temp = new HashMap<>( );
		List<RFC11> list = new LinkedList<>( );
		
		StringBuilder sql = new StringBuilder( );
		sql.append( "SELECT " );
		sql.append( "  DISTINCT " );
		sql.append( "  E.NOMBRE  AS ESPECTACULO, " );
		sql.append( "  Z.FECHA, " );
		sql.append( "  L.NOMBRE  AS LUGAR, " );
		sql.append( "  Z.ROL, " );
		sql.append( "  NUM_BOLETAS " );
		sql.append( "FROM ( SELECT " );
		sql.append( "         F.*, " );
		sql.append( "         U.ROL, " );
		sql.append( "         COUNT( * ) AS NUM_BOLETAS " );
		sql.append( "       FROM " );
		sql.append( "         FUNCIONES F " );
		sql.append( "         INNER JOIN BOLETAS B " );
		sql.append( "           ON B.FECHA = F.FECHA AND B.ID_LUGAR = F.ID_LUGAR " );
		sql.append( "         INNER JOIN USUARIOS U " );
		sql.append( "           ON U.IDENTIFICACION = B.ID_USUARIO AND U.TIPO_IDENTIFICACION = B.ID_TIPO " );
		sql.append( "         INNER JOIN " );
		sql.append( "         LOCALIDADES L " );
		sql.append( "           ON B.ID_LOCALIDAD = L.ID " );
		sql.append( localidad != null && !localidad.isEmpty( ) ? String.format( " WHERE L.NOMBRE = %s ", localidad ) : "" );
		sql.append( "       GROUP BY F.FECHA, F.ID_LUGAR, ID_ESPECTACULO, SE_REALIZA, ROL ) Z " );
		sql.append( "  INNER JOIN " );
		sql.append( "  ESPECTACULOS E " );
		sql.append( "    ON E.ID = Z.ID_ESPECTACULO " );
		sql.append( "  INNER JOIN " );
		sql.append( "  ESPECTACULO_REQUERIMIENTO ER " );
		sql.append( "    ON E.ID = ER.ID_ESPECTACULO " );
		sql.append( "  INNER JOIN " );
		sql.append( "  REQUERIMIENTOS_TECNICOS RT " );
		sql.append( "    ON ER.ID_REQUERIMIENTO = RT.ID " );
		sql.append( "  INNER JOIN " );
		sql.append( "  LUGARES L " );
		sql.append( "    ON L.ID = Z.ID_LUGAR " );
		sql.append( "WHERE 1 = 1 " );
		if( !requerimientosTecnicos.isEmpty( ) )
		{
			sql.append( "AND ( " );
			for( String requerimiento : requerimientosTecnicos )
			{
				sql.append( String.format( " RT.NOMBRE = %s OR", requerimiento ) );
			}
			sql.deleteCharAt( sql.length( ) );
			sql.deleteCharAt( sql.length( ) );
			sql.append( " )" );
		}
		if( hInicio != null && hFin != null )
		{
			sql.append( String.format( "AND TO_CAR( Z.FECHA, 'HH24:MI' ) BETWEEN %s AND %s ", toTime( hInicio ), toTime( hFin ) ) );
		}
		if( fInicio != null && fEnd != null )
		{
			sql.append( String.format( "AND Z.FECHA BETWEEN %s AND %s ", toDate( fInicio ), toDate( fEnd ) ) );
		}
		
		System.out.println( sql.toString( ) );
		
		PreparedStatement s = connection.prepareStatement( sql.toString( ) );
		ResultSet rs = s.executeQuery( );
		while( rs.next( ) )
		{
			String nombreEspectaculo = rs.getString( "ESPECTACULO" );
			Date fecha = rs.getTimestamp( "FECHA" );
			String lugar = rs.getString( "LUGAR" );
			String rol = rs.getString( "ROL" );
			Integer num_boletas = rs.getInt( "NUM_BOLETAS" );
			
			RFC11 t = temp.get( rfc11AsKey( nombreEspectaculo, fecha, lugar ) );
			if( t == null )
			{
				t = new RFC11( );
				t.setNombreEspectaculo( nombreEspectaculo );
				t.setFecha( fecha );
				t.setNombreLugar( lugar );
			}
			if( rol.equals( Usuario.USUARIO_REGISTRADO ) )
			{
				t.setBoletasUsuariosRegistrados( num_boletas );
			}
			int total = t.getTotalBoletas( );
			total += num_boletas;
			t.setTotalBoletas( total );
			
			temp.put( rfc11AsKey( nombreEspectaculo, fecha, lugar ), t );
		}
		
		list.addAll( temp.values( ) );
		
		rs.close( );
		s.close( );
		return list;
	}
	
	private String rfc11AsKey( String nombreEspectaculo, Date fecha, String lugar )
	{
		return String.format( "%s;%s;%s", nombreEspectaculo, toDateTime( fecha ), lugar );
	}
}