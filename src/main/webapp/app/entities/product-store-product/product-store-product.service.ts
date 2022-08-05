import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductStoreProduct } from 'app/shared/model/product-store-product.model';

type EntityResponseType = HttpResponse<IProductStoreProduct>;
type EntityArrayResponseType = HttpResponse<IProductStoreProduct[]>;

@Injectable({ providedIn: 'root' })
export class ProductStoreProductService {
  public resourceUrl = SERVER_API_URL + 'api/product-store-products';

  constructor(protected http: HttpClient) {}

  create(productStoreProduct: IProductStoreProduct): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productStoreProduct);
    return this.http
      .post<IProductStoreProduct>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(productStoreProduct: IProductStoreProduct): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productStoreProduct);
    return this.http
      .put<IProductStoreProduct>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProductStoreProduct>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProductStoreProduct[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(productStoreProduct: IProductStoreProduct): IProductStoreProduct {
    const copy: IProductStoreProduct = Object.assign({}, productStoreProduct, {
      fromDate: productStoreProduct.fromDate && productStoreProduct.fromDate.isValid() ? productStoreProduct.fromDate.toJSON() : undefined,
      thruDate: productStoreProduct.thruDate && productStoreProduct.thruDate.isValid() ? productStoreProduct.thruDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fromDate = res.body.fromDate ? moment(res.body.fromDate) : undefined;
      res.body.thruDate = res.body.thruDate ? moment(res.body.thruDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((productStoreProduct: IProductStoreProduct) => {
        productStoreProduct.fromDate = productStoreProduct.fromDate ? moment(productStoreProduct.fromDate) : undefined;
        productStoreProduct.thruDate = productStoreProduct.thruDate ? moment(productStoreProduct.thruDate) : undefined;
      });
    }
    return res;
  }
}
