package rest;

import tm.UsuarioTM;
import vos.Usuario;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path( "usuarios" )
public class UsuarioServices extends Services
{
	@POST
	public Response createUsuario( Usuario usuario )
	{
		UsuarioTM tm = new UsuarioTM( getPath( ) );
		try
		{
			usuario = tm.createUsuario( usuario );
		}
		catch( SQLException e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
		return Response.status( 200 ).entity( usuario ).build( );
	}
	
	@GET
	public Response getUsuario( )
	{
		List<Usuario> list;
		UsuarioTM tm = new UsuarioTM( getPath( ) );
		try
		{
			list = tm.getUsuarios( );
		}
		catch( SQLException e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
		return Response.status( 200 ).entity( list ).build( );
	}
	
	@GET
	@Path( "{id}" )
	public Response getUsuario( @PathParam( "id" ) Long id )
	{
		Usuario us;
		UsuarioTM tm = new UsuarioTM( getPath( ) );
		try
		{
			us = tm.getUsuario( id );
		}
		catch( SQLException e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
		return Response.status( 200 ).entity( us ).build( );
	}
	
	@PUT
	@Path( "{id}" )
	public Response updateUsuario( @PathParam( "id" ) Long id, Usuario us )
	{
		Usuario l;
		UsuarioTM tm = new UsuarioTM( getPath( ) );
		try
		{
			l = tm.updateUsuario( id, us );
		}
		catch( SQLException e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
		return Response.status( 200 ).entity( l ).build( );
	}
	
	@DELETE
	@Path( "{id}" )
	public Response deleteUsuario( @PathParam( "id" ) Long id )
	{
		UsuarioTM tm = new UsuarioTM( getPath( ) );
		try
		{
			tm.deleteUsuario( id );
		}
		catch( SQLException e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
		return Response.status( 200 ).build( );
	}
}