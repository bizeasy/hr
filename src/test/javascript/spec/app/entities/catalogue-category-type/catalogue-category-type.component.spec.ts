import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { CatalogueCategoryTypeComponent } from 'app/entities/catalogue-category-type/catalogue-category-type.component';
import { CatalogueCategoryTypeService } from 'app/entities/catalogue-category-type/catalogue-category-type.service';
import { CatalogueCategoryType } from 'app/shared/model/catalogue-category-type.model';

describe('Component Tests', () => {
  describe('CatalogueCategoryType Management Component', () => {
    let comp: CatalogueCategoryTypeComponent;
    let fixture: ComponentFixture<CatalogueCategoryTypeComponent>;
    let service: CatalogueCategoryTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [CatalogueCategoryTypeComponent],
      })
        .overrideTemplate(CatalogueCategoryTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatalogueCategoryTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatalogueCategoryTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CatalogueCategoryType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.catalogueCategoryTypes && comp.catalogueCategoryTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
