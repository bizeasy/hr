import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { CatalogueCategoryTypeDetailComponent } from 'app/entities/catalogue-category-type/catalogue-category-type-detail.component';
import { CatalogueCategoryType } from 'app/shared/model/catalogue-category-type.model';

describe('Component Tests', () => {
  describe('CatalogueCategoryType Management Detail Component', () => {
    let comp: CatalogueCategoryTypeDetailComponent;
    let fixture: ComponentFixture<CatalogueCategoryTypeDetailComponent>;
    const route = ({ data: of({ catalogueCategoryType: new CatalogueCategoryType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [CatalogueCategoryTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CatalogueCategoryTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatalogueCategoryTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load catalogueCategoryType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.catalogueCategoryType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
